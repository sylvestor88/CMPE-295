import os
import smtplib
from cassandra.cluster import Cluster


def connect_cass():

    contact_point = ['127.0.0.1']
    return Cluster(contact_point)


def list_devices():

    cluster = connect_cass()
    session = cluster.connect('pimaskapp')
    result = session.execute("select * from connected_devices")
    session.shutdown()
    cluster.shutdown()
    return result


def list_emails():

    cluster = connect_cass()
    session = cluster.connect('pimaskapp')
    result = session.execute("select * from users")
    session.shutdown()
    cluster.shutdown()
    return [str(x.email) for x in result if x.notification]


def send_mail(ip, name):

    fromaddr = 'sylvestor.george88@yahoo.com'
    toaddr  = list_emails()
    msg = "Device with name " + name + " and IP " + ip + " is not reachable. Please check if its powered ON or connected to your network."
    # Credentials
    username = 'sylvestor.george88@yahoo.com'
    password = 'PiMask2015'

    if len(toaddr) != 0:
    # The actual mail send
        server = smtplib.SMTP('smtp.mail.yahoo.com:587')
        server.starttls()
        server.login(username,password)
        server.sendmail(fromaddr, toaddr, msg)
        server.quit()


def change_status(ip, status):

    cluster = connect_cass()
    session = cluster.connect('pimaskapp')
    result = session.execute("UPDATE connected_devices set device_status = \'" + status + "\' WHERE device_ip = \'" + ip + "\'")
    session.shutdown()
    cluster.shutdown()


def check_status():

    devices = list_devices()
    for device in devices:
        response = os.system("ping -c 1 " + device.device_ip)
        if response == 0:
            change_status(device.device_ip, "Up")
        else:
            change_status(device.device_ip, "Down")
            send_mail(device.device_ip, device.name)

check_status()