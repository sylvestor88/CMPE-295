package beans;

import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "pimaskapp", name = "connected_devices")
public class Device {
	
	@PartitionKey
	private UUID device_id;
	private String device_name;
	private String device_ip;
	private boolean notification;
	private String data_location;
	
//===============begin of the conf variables =================================
	private String working_schedule_type = "outside";
	private String webcam_resolution ="100";
	private String enabled ="on";
	private String name ="Camera1";
	private String network_server ;
	private String motion_detection ="on";
	private String preserve_pictures ="0";
	private String preserve_movies ="1";
	private String storage_device ="custom-path";
	private String network_username ;
	private String network_password ;
	private String id ="1";
	private String working_schedule ;
	private String webcam_server_resize ="off";
	private String network_share_name ;
//configuration details which needs to be obtained from the user
	private String lightswitch ="50";
	private String stream_authenticationuser =":pass";
	private String snapshot_interval ="0";
	private String ffmpeg_output_movies ="on";
	private String stream_motion ="off";
	private String target_dir ="/data/output/";
	private String text_double ="off";
	private String height ="240";
	private String stream_auth_method ="0";
	private String width ="320";
	private String stream_quality ="85";
	private String threshold ="1999";
	private String quality ="85";
	private String post_capture ="4";
	private String noise_level ="31";
	private String on_event_end ="/usr/bin/python /usr/lib/python2.7/site-packages/motioneye/meyectl.pyc relayevent -c /data/etc/motioneye.conf -l stop %t";
	private String text_left = "Camera1";
	private String pre_capture ="2";
	private String noise_tune ="on";
	private String picture_filename ="%Y-%m-%d/%H-%M-%S";
	private String locate_motion_style ="redbox";
	private String locate_motion_mode ="off";
	private String contrast ="0";
	private String stream_maxrate ="5";
	private String output_pictures ="off";
	private String hue ="0";
	private String saturation ="0";
	private String stream_localhost ="off";
	private String videodevice ="/dev/video0";
	private String ffmpeg_variable_bitrate ="0";
	private String ffmpeg_video_codec ="msmpeg4";
	private String text_changes ="off";
	private String movie_filename ="%Y-%m-%d/%H-%M-%S";
	private String event_gap ="10";
	private String auto_brightness ="off";
	private String stream_port ="8081";
	private String rotate ="0";
	private String text_right ="%Y-%m-%d\\n%T";
	private String on_event_start ="/usr/bin/python /usr/lib/python2.7/site-packages/motioneye/meyectl.pyc relayevent -c /data/etc/motioneye.conf -l start %t";
	private String ffmpeg_bps ="44544";
	private String brightness ="0";
	private String framerate ="2";
	private String max_movie_time ="30";
	private String emulate_motion ="off";
	private String snapshot_filename ;
	private String minimum_motion_frames ="10";
// getter and setter methods for the configurable variables.	
	public String getWorking_schedule_type() {
		return working_schedule_type;
	}

	public void setWorking_schedule_type(String working_schedule_type) {
		this.working_schedule_type = working_schedule_type;
	}

	public String getWebcam_resolution() {
		return webcam_resolution;
	}

	public void setWebcam_resolution(String webcam_resolution) {
		this.webcam_resolution = webcam_resolution;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNetwork_server() {
		return network_server;
	}

	public void setNetwork_server(String network_server) {
		this.network_server = network_server;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMotion_detection() {
		return motion_detection;
	}

	public void setMotion_detection(String motion_detection) {
		this.motion_detection = motion_detection;
	}

	public String getNetwork_username() {
		return network_username;
	}

	public void setNetwork_username(String network_username) {
		this.network_username = network_username;
	}

	public String getStorage_device() {
		return storage_device;
	}

	public void setStorage_device(String storage_device) {
		this.storage_device = storage_device;
	}

	public String getPreserve_movies() {
		return preserve_movies;
	}

	public void setPreserve_movies(String preserve_movies) {
		this.preserve_movies = preserve_movies;
	}

	public String getPreserve_pictures() {
		return preserve_pictures;
	}

	public void setPreserve_pictures(String preserve_pictures) {
		this.preserve_pictures = preserve_pictures;
	}

	public String getNetwork_share_name() {
		return network_share_name;
	}

	public void setNetwork_share_name(String network_share_name) {
		this.network_share_name = network_share_name;
	}

	public String getWorking_schedule() {
		return working_schedule;
	}

	public void setWorking_schedule(String working_schedule) {
		this.working_schedule = working_schedule;
	}

	public String getWebcam_server_resize() {
		return webcam_server_resize;
	}

	public void setWebcam_server_resize(String webcam_server_resize) {
		this.webcam_server_resize = webcam_server_resize;
	}

	public String getNetwork_password() {
		return network_password;
	}

	public void setNetwork_password(String network_password) {
		this.network_password = network_password;
	}

	public String getLightswitch() {
		return lightswitch;
	}

	public void setLightswitch(String lightswitch) {
		this.lightswitch = lightswitch;
	}

	public String getSnapshot_interval() {
		return snapshot_interval;
	}

	public void setSnapshot_interval(String snapshot_interval) {
		this.snapshot_interval = snapshot_interval;
	}

	public String getMinimum_motion_frames() {
		return minimum_motion_frames;
	}

	public void setMinimum_motion_frames(String minimum_motion_frames) {
		this.minimum_motion_frames = minimum_motion_frames;
	}

	public String getStream_motion() {
		return stream_motion;
	}

	public void setStream_motion(String stream_motion) {
		this.stream_motion = stream_motion;
	}

	public String getTarget_dir() {
		return target_dir;
	}

	public void setTarget_dir(String target_dir) {
		this.target_dir = target_dir;
	}

	public String getText_double() {
		return text_double;
	}

	public void setText_double(String text_double) {
		this.text_double = text_double;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getStream_auth_method() {
		return stream_auth_method;
	}

	public void setStream_auth_method(String stream_auth_method) {
		this.stream_auth_method = stream_auth_method;
	}

	public String getStream_quality() {
		return stream_quality;
	}

	public void setStream_quality(String stream_quality) {
		this.stream_quality = stream_quality;
	}

	public String getNoise_tune() {
		return noise_tune;
	}

	public void setNoise_tune(String noise_tune) {
		this.noise_tune = noise_tune;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public String getPost_capture() {
		return post_capture;
	}

	public void setPost_capture(String post_capture) {
		this.post_capture = post_capture;
	}

	public String getNoise_level() {
		return noise_level;
	}

	public void setNoise_level(String noise_level) {
		this.noise_level = noise_level;
	}

	public String getFramerate() {
		return framerate;
	}

	public void setFramerate(String framerate) {
		this.framerate = framerate;
	}

	public String getText_left() {
		return text_left;
	}

	public void setText_left(String text_left) {
		this.text_left = text_left;
	}

	public String getPre_capture() {
		return pre_capture;
	}

	public void setPre_capture(String pre_capture) {
		this.pre_capture = pre_capture;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getPicture_filename() {
		return picture_filename;
	}

	public void setPicture_filename(String picture_filename) {
		this.picture_filename = picture_filename;
	}

	public String getLocate_motion_style() {
		return locate_motion_style;
	}

	public void setLocate_motion_style(String locate_motion_style) {
		this.locate_motion_style = locate_motion_style;
	}

	public String getLocate_motion_mode() {
		return locate_motion_mode;
	}

	public void setLocate_motion_mode(String locate_motion_mode) {
		this.locate_motion_mode = locate_motion_mode;
	}

	public String getContrast() {
		return contrast;
	}

	public void setContrast(String contrast) {
		this.contrast = contrast;
	}

	public String getStream_maxrate() {
		return stream_maxrate;
	}

	public void setStream_maxrate(String stream_maxrate) {
		this.stream_maxrate = stream_maxrate;
	}

	public String getOutput_pictures() {
		return output_pictures;
	}

	public void setOutput_pictures(String output_pictures) {
		this.output_pictures = output_pictures;
	}

	public String getHue() {
		return hue;
	}

	public void setHue(String hue) {
		this.hue = hue;
	}

	public String getSaturation() {
		return saturation;
	}

	public void setSaturation(String saturation) {
		this.saturation = saturation;
	}

	public String getStream_localhost() {
		return stream_localhost;
	}

	public void setStream_localhost(String stream_localhost) {
		this.stream_localhost = stream_localhost;
	}

	public String getVideodevice() {
		return videodevice;
	}

	public void setVideodevice(String videodevice) {
		this.videodevice = videodevice;
	}

	public String getFfmpeg_variable_bitrate() {
		return ffmpeg_variable_bitrate;
	}

	public void setFfmpeg_variable_bitrate(String ffmpeg_variable_bitrate) {
		this.ffmpeg_variable_bitrate = ffmpeg_variable_bitrate;
	}

	public String getFfmpeg_video_codec() {
		return ffmpeg_video_codec;
	}

	public void setFfmpeg_video_codec(String ffmpeg_video_codec) {
		this.ffmpeg_video_codec = ffmpeg_video_codec;
	}

	public String getText_changes() {
		return text_changes;
	}

	public void setText_changes(String text_changes) {
		this.text_changes = text_changes;
	}

	public String getMovie_filename() {
		return movie_filename;
	}

	public void setMovie_filename(String movie_filename) {
		this.movie_filename = movie_filename;
	}

	public String getAuto_brightness() {
		return auto_brightness;
	}

	public void setAuto_brightness(String auto_brightness) {
		this.auto_brightness = auto_brightness;
	}

	public String getOn_event_end() {
		return on_event_end;
	}

	public void setOn_event_end(String on_event_end) {
		this.on_event_end = on_event_end;
	}

	public String getStream_port() {
		return stream_port;
	}

	public void setStream_port(String stream_port) {
		this.stream_port = stream_port;
	}

	public String getRotate() {
		return rotate;
	}

	public void setRotate(String rotate) {
		this.rotate = rotate;
	}

	public String getText_right() {
		return text_right;
	}

	public void setText_right(String text_right) {
		this.text_right = text_right;
	}

	public String getOn_event_start() {
		return on_event_start;
	}

	public void setOn_event_start(String on_event_start) {
		this.on_event_start = on_event_start;
	}

	public String getFfmpeg_output_movies() {
		return ffmpeg_output_movies;
	}

	public void setFfmpeg_output_movies(String ffmpeg_output_movies) {
		this.ffmpeg_output_movies = ffmpeg_output_movies;
	}

	public String getFfmpeg_bps() {
		return ffmpeg_bps;
	}

	public void setFfmpeg_bps(String ffmpeg_bps) {
		this.ffmpeg_bps = ffmpeg_bps;
	}

	public String getBrightness() {
		return brightness;
	}

	public void setBrightness(String brightness) {
		this.brightness = brightness;
	}

	public String getEvent_gap() {
		return event_gap;
	}

	public void setEvent_gap(String event_gap) {
		this.event_gap = event_gap;
	}

	public String getMax_movie_time() {
		return max_movie_time;
	}

	public void setMax_movie_time(String max_movie_time) {
		this.max_movie_time = max_movie_time;
	}

	public String getEmulate_motion() {
		return emulate_motion;
	}

	public void setEmulate_motion(String emulate_motion) {
		this.emulate_motion = emulate_motion;
	}

	public String getSnapshot_filename() {
		return snapshot_filename;
	}

	public void setSnapshot_filename(String snapshot_filename) {
		this.snapshot_filename = snapshot_filename;
	}

	public String getStream_authenticationuser() {
		return stream_authenticationuser;
	}

	public void setStream_authenticationuser(String stream_authenticationuser) {
		this.stream_authenticationuser = stream_authenticationuser;
	}

//======================== end of conf variables =====================================
	public UUID getDevice_id() {
		return device_id;
	}
	public void setDevice_id(UUID device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDevice_ip() {
		return device_ip;
	}
	public void setDevice_ip(String device_ip) {
		this.device_ip = device_ip;
	}
	public boolean isNotification() {
		return notification;
	}
	public void setNotification(boolean notification) {
		this.notification = notification;
	}

	public String getData_location() {
		return data_location;
	}

	public void setData_location(String data_location) {
		this.data_location = data_location;
	}
	
	
}
