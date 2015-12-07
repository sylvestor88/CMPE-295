package piserver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import beans.Device;

public class ConfFileTemplate {
	
	public static String createConfigFile(Device a){
	
	 String conf_string = "# @working_schedule_type "+a.getWorking_schedule_type()+"\n"+
			"# @webcam_resolution "+a.getWebcam_resolution()+"\n"+
			"# @enabled "+a.getEnabled()+"\n"+
			"# @name "+a.getName()+"\n"+
			"# @network_share_name "+a.getNetwork_share_name()+"\n"+
			"# @network_server "+a.getNetwork_server()+"\n"+
			"# @motion_detection "+a.getMotion_detection()+"\n"+
			"# @preserve_pictures "+a.getPreserve_pictures()+"\n"+
			"# @storage_device "+a.getStorage_device()+"\n"+
			"# @preserve_movies "+a.getPreserve_movies()+"\n"+
			"# @network_username "+a.getNetwork_username()+"\n"+
			"# @network_password "+a.getNetwork_password()+"\n"+
			"# @id "+a.getId()+"\n"+
			"# @working_schedule "+a.getWorking_schedule()+"\n"+
			"# @webcam_server_resize "+a.getWebcam_server_resize()+"\n\n\n"+


			"lightswitch "+a.getLightswitch()+"\n"+
			"stream_authentication user:"+a.getStream_authenticationuser()+"\n"+
			"snapshot_interval "+a.getSnapshot_interval()+"\n"+
			"ffmpeg_output_movies "+a.getFfmpeg_output_movies()+"\n"+
			"stream_motion "+a.getStream_motion()+"\n"+
			"target_dir "+a.getTarget_dir()+"\n"+
			"text_double "+a.getText_double()+"\n"+
			"height "+a.getHeight()+"\n"+
			"stream_auth_method "+a.getStream_auth_method()+"\n"+
			"width "+a.getWidth()+"\n"+
			"stream_quality "+a.getStream_quality()+"\n"+
			"threshold "+a.getThreshold()+"\n"+
			"quality "+a.getQuality()+"\n"+
			"post_capture "+a.getPost_capture()+"\n"+
			"noise_level "+a.getNoise_level()+"\n"+
			"on_event_end "+a.getOn_event_end()+"\n"+
			"text_left "+a.getText_left()+"\n"+
			"pre_capture "+a.getPre_capture()+"\n"+
			"noise_tune "+a.getNoise_tune()+"\n"+
			"picture_filename "+a.getPicture_filename()+"\n"+
			"locate_motion_style "+a.getLocate_motion_style()+"\n"+
			"locate_motion_mode "+a.getLocate_motion_mode()+"\n"+
			"contrast "+a.getContrast()+"\n"+
			"stream_maxrate "+a.getStream_maxrate()+"\n"+
			"output_pictures "+a.getOutput_pictures()+"\n"+
			"hue "+a.getHue()+"\n"+
			"saturation "+a.getSaturation()+"\n"+
			"stream_localhost "+a.getStream_localhost()+"\n"+
			"videodevice "+a.getVideodevice()+"\n"+
			"ffmpeg_variable_bitrate "+a.getFfmpeg_variable_bitrate()+"\n"+
			"ffmpeg_video_codec "+a.getFfmpeg_video_codec()+"\n"+
			"text_changes "+a.getText_changes()+"\n"+
			"movie_filename "+a.getMovie_filename()+"\n"+
			"event_gap "+a.getEvent_gap()+"\n"+
			"auto_brightness "+a.getAuto_brightness()+"\n"+
			"stream_port "+a.getStream_port()+"\n"+
			"rotate "+a.getRotate()+"\n"+
			"text_right "+a.getText_right()+"\n"+
			"on_event_start "+a.getOn_event_start()+"\n"+
			"ffmpeg_bps "+a.getFfmpeg_bps()+"\n"+
			"brightness "+a.getBrightness()+"\n"+
			"framerate "+a.getFramerate()+"\n"+
			"max_movie_time "+a.getMax_movie_time()+"\n"+
			"emulate_motion "+a.getEmulate_motion()+"\n"+
			"minimum_motion_frames "+a.getMinimum_motion_frames()+"\n"+
			"snapshot_filename "+a.getSnapshot_filename();
	 
//=============End of the string build && begin of the Writing file ===============
	
	return writeConffile(conf_string, "/home/pi/conf_files/thread-1.conf");
	 
	}
	
//==============Writing config file to the directory =====================	
	
	public static String writeConffile(String conf,String location){
		try {
			File file = new File(location);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(conf);
			bw.close();

			return location; //retuns the location of the file when file has been created successfully

		} catch (IOException e) {
			e.printStackTrace();
			return "failed to write to file";
		}
	}
}


