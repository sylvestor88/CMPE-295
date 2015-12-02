import time
import smtplib
from watchdog.observers import Observer
from watchdog.events import PatternMatchingEventHandler
from cassandra.cluster import Cluster


def connect_cass():

    contact_point = ['127.0.0.1']
    return Cluster(contact_point)


def list_directories():

    cluster = connect_cass()
    session = cluster.connect('pimaskapp')
    result = session.execute("select * from connected_devices")
    session.shutdown()
    cluster.shutdown()
    return [x.data_location for x in result]


def list_emails():

    cluster = connect_cass()
    session = cluster.connect('pimaskapp')
    result = session.execute("select * from users")
    session.shutdown()
    cluster.shutdown()
    return [str(x.userid) for x in result if x.notification]


def send_mail(event):

    fromaddr = 'sylvestor.george88@yahoo.com'
    toaddr  = list_emails()
    msg = 'New File created at: ' + event.src_path


    # Credentials
    username = ''
    password = ''

    # The actual mail send
    server = smtplib.SMTP('smtp.mail.yahoo.com:587')
    server.starttls()
    server.login(username,password)
    server.sendmail(fromaddr, toaddr, msg)
    server.quit()


class MyHandler(PatternMatchingEventHandler):

    patterns = ["*.txt"]

    def process(self, event):
        """
        event.event_type
            'modified' | 'created' | 'moved' | 'deleted'
        event.is_directory
            True | False
        event.src_path
            path/to/observed/file
        """
        # the file will be processed there
        #print event.src_path, event.event_type  # print now only for degug
        send_mail(event)

    def on_created(self, event):
        self.process(event)


if __name__ == '__main__':

    directory_list = list_directories()

    for i in directory_list:
        path = str(i)
        observer = Observer()
        observer.schedule(MyHandler(), path)
        observer.start()

    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        observer.stop()

    observer.join()
