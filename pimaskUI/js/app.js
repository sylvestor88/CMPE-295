var app = angular.module('PiMask',['ngRoute'])
.config(function($routeProvider){
	$routeProvider.when('/connected_devices', {
		templateUrl: '/templates/connected-devices.html',
		controller: 'ShowDevicesController',
		controllerAs:'showDevicesCtrl'
	})
	.when('/find_picam_devices', {
		templateUrl: '/templates/find-picam-devices.html',
		controller: 'FindPiCamController',
		controllerAs:'findPiCamCtrl'
	})
	.when('/find_other_devices', {
		templateUrl: '/templates/find-other-devices.html',
		controller: 'FindOtherController',
		controllerAs:'findOtherCtrl'
	})
	.when('/configure_picam_device/:id', {
		templateUrl: 'templates/configure-picam-devices.html',
		controller: 'ConfigurePiCamController',
		controllerAs: 'configurePiCamCtrl'
	})
	.when('/configure_other_device/:id', {
		templateUrl: 'templates/configure-other-devices.html',
		controller: 'ConfigureOtherController',
		controllerAs: 'configureOtherCtrl'
	})
	.when('/edit_picam_device/:id', {
		templateUrl: 'templates/edit-picam-devices.html',
		controller: 'EditPiCamController',
		controllerAs: 'editPiCamCtrl'
	})
	.when('/edit_other_device/:id', {
		templateUrl: 'templates/edit-devices.html',
		controller: 'EditOtherController',
		controllerAs: 'editOtherCtrl'
	})
	.when('/add_user', {
		templateUrl: 'templates/add-user.html',
		controller: 'AddUserController',
		controllerAs: 'AddUserCtrl'
	})
	.when('/edit_user/:id', {
		templateUrl: 'templates/edit-user.html',
		controller: 'EditUserController',
		controllerAs: 'EditUserCtrl'
	})
	.when('/', {
		redirectTo:'/connected_devices'
	})
});

app.controller('FindPiCamController', function($scope, $http){
	$scope.names = {};
//	$scope.names = [{"ip": "192.168.100.23", "hostname": "PiCamera"}, {"ip": "192.168.100.27", "hostname": "Android-odqe25242eq3d3"}, {"ip": "192.168.100.35", "hostname": "ASUS-Router"}];
	$http.get('http://localhost:8080/pimask/find_picam_devices')
	 .success(function(response){
	 	$scope.names = response;
	 	if(response.length == 0)
	 	{
	 		alert("No Devices Found on the network.");
	 	}
	 })
	 .error(function(){
	 	alert("Something went wrong. Please try again!");
	 });
});

app.controller('FindOtherController', function($scope, $http){
	$scope.names = {};
//	$scope.names = [{"ip": "192.168.100.23", "hostname": "PiCamera"}, {"ip": "192.168.100.27", "hostname": "Android-odqe25242eq3d3"}, {"ip": "192.168.100.35", "hostname": "ASUS-Router"}];
	$http.get('http://localhost:8080/pimask/find_other_devices')
	 .success(function(response){
	 	$scope.names = response;
	 	if(response.length == 0)
	 	{
	 		//alert("No Devices Found on the network.");
	 		swal({   title: "Sorry!",   text: "No Devices Found on the network. Check your internet connection.",   type: "error",   confirmButtonText: "OK" });
	 	}
	 })
	 .error(function(){
	 	//alert("No Devices Found. Please try again!");
	 	swal({   title: "Sorry!",   text: "No Devices Found on the network. Check your internet connection.",   type: "error",   confirmButtonText: "OK" });
	 });
});

app.controller('ShowDevicesController', function($scope, $http, $route, $routeParams){

	$scope.names = {};	
	//$scope.names = [{"device_ip": "192.168.100.23", "device_name": "PiCamera"}, {"device_ip": "192.168.100.27", "device_name": "Android-odqe25242eq3d3"}, {"device_ip": "192.168.100.35", "device_name": "ASUS-Router"}];
	$http.get('http://localhost:8080/pimask/connected')
	 .success(function(response){
	 	$scope.names = response;
	 	if(response.length == 0)
	 	{
	 		//alert("No devices connected to the server.");
	 		swal({   title: "Sorry!",   text: "No devices are currently connected to the server.",   type: "error",   confirmButtonText: "OK" });
	 	}
	 })
	 .error(function(response){
	 	//alert("Something went wrong. Please try again!");
	 	swal({   title: "Error!",   text: "Something went wrong. Please try again!",   type: "error",   confirmButtonText: "OK" });

	 });

	 

	 $scope.deleteDevice = function(name){
	 		swal({	title: "Are you sure?",
	 				text: "Device " + name.name + " and all its files will be deleted permanently. Are you sure?",
	 				type: "warning",   showCancelButton: true,   confirmButtonColor: "#DD6B55",
	 				confirmButtonText: "Yes, delete it!",   closeOnConfirm: false },
	 			function(){ 
	 				var URL = 'http://localhost:8080/pimask/deleteDevice/' + String(name.device_ip);	
					
					$http({
						method: 'DELETE',
						url: URL
					})  
					.success(function(data){
					//alert(data.message);
					//swal("Deleted!", data.message, "success"); 
					swal({   title: "Device Deleted!",   text: data.message,   timer: 5000, type: "success",   showConfirmButton: false });
					})
					.error(function(data){
					//alert(data.message);
					swal("Oops!", data.message, "error");
					});
				});
	 			$route.reload();
			}; 
});

app.controller('ConfigurePiCamController', function($scope, $http, $routeParams, $location){
	
	$scope.device={};
	$scope.ip = $routeParams.id;

	$scope.submitForm = function(){
		var brightnessInfo = parseInt($scope.device.brightness*2.55);
		var contrastInfo = parseInt($scope.device.contrast*2.55);
		var saturationInfo = parseInt($scope.device.saturation*2.55);
		var video_resolutionInfo = $scope.device.video_resolution.split("x");
		var heightInfo = parseInt(video_resolutionInfo[1]);
		var widthInfo = parseInt(video_resolutionInfo[0]);
		var preserve_moviesInfo = parseInt($scope.device.preserve_movies);
		var video_rotationInfo = parseInt($scope.device.video_rotation);

		var working_scheduleInfo = String($scope.device.working_schedule.monday+"|"+$scope.device.working_schedule.tuesday+"|"
			+$scope.device.working_schedule.wednesday+"|"+$scope.device.working_schedule.thursday+"|"
			+$scope.device.working_schedule.friday+"|"+$scope.device.working_schedule.saturday+"|"
			+$scope.device.working_schedule.sunday);
		
		var deviceInfo = {
 		name: $scope.device.name,
 		device_ip: $routeParams.id,
 		device_type: "PiCam",
 		live_streaming: "http://" + $routeParams.id + ":8081",
 		brightness: brightnessInfo,
 		contrast: contrastInfo ,
 		saturation: saturationInfo,
 		height: heightInfo,
 		width: widthInfo,
 		text_left: $scope.device.name,
 		video_rotation: video_rotationInfo,
 		frame_rate: $scope.device.frame_rate,
 		stream_max_rate: $scope.device.stream_max_rate,
 		stream_quality: $scope.device.stream_quality,
 		max_movie_time: $scope.device.max_movie_time,
 		preserve_movies: preserve_moviesInfo,
 		motion_gap: $scope.device.motion_gap,
 		pre_capture: $scope.device.pre_capture,
 		post_capture: $scope.device.post_capture,
 		minimum_motion_frames: $scope.device.minimum_motion_frames,
 		working_schedule: working_scheduleInfo,
 		notification: $scope.device.notification
 		};

 		var wirelessInfo = {
 			ssid: $scope.device.ssid,
 			password: $scope.device.password
 		};

		$http({
			method: 'POST',
			url: 'http://localhost:8080/pimask/save_picam_device',
			headers: {'Content-Type': 'application/json'},
			data: deviceInfo
		})
		.success(function(data){
			//alert(data.message);
			swal({   title: "Device Saved!",   text: data.message,   timer: 5000, type: "success",   showConfirmButton: false });
			$location('/connected_devices')
		})
		.error(function(data){
			//alert(data.message);
			swal("Oops!", data.message, "error"); 
		});

		$scope.device={};
	};

});

app.controller('ConfigureOtherController', function($scope, $http, $routeParams, $location){
	
	$scope.device={};
	$scope.ip = $routeParams.id;

	$scope.submitForm = function(){	
		
		var deviceInfo = {
 		name: $scope.device.name,
 		device_ip: $routeParams.id,
 		device_type: "Third Party",
 		live_streaming: "http://" + $routeParams.id + ":8081",
 		notification: $scope.device.notification
 		};

		$http({
			method: 'POST',
			url: 'http://localhost:8080/pimask/save_other_device',
			headers: {'Content-Type': 'application/json'},
			data: deviceInfo
		})
		.success(function(data){
			swal({   title: "Device Saved!",   text: data.message,   timer: 5000, type: "success",   showConfirmButton: false });
			$location.path() == '/connected_devices';
		})
		.error(function(data){
			swal("Oops!", data.message, "error");
		});

		$scope.device={};
	};
});

app.controller('EditPiCamController', function($scope, $http, $routeParams, $location){
	
	$scope.device = {};
	$scope.ip = $routeParams.id;

	$http({
			method: 'GET',
			url: 'http://localhost:8080/pimask/findDevice/' + $scope.ip
		})
		.success(function(data){
			$scope.device = data;

			$scope.device.brightness = $scope.device.brightness/2.55;
			$scope.device.contrast = $scope.device.contrast/2.55;
			$scope.device.saturation = $scope.device.saturation/2.55;
			$scope.device.video_resolution = String($scope.device.width + "x" + $scope.device.height);
			$scope.device.preserve_movies = String($scope.device.preserve_movies);
			$scope.device.video_rotation = String($scope.device.video_rotation);
			$scope.device.working_schedule = $scope.device.working_schedule.split("|");
			$scope.device.working_schedule.monday = $scope.device.working_schedule[0];
			$scope.device.working_schedule.tuesday = $scope.device.working_schedule[1];
			$scope.device.working_schedule.wednesday = $scope.device.working_schedule[2];
			$scope.device.working_schedule.thursday = $scope.device.working_schedule[3];
			$scope.device.working_schedule.friday = $scope.device.working_schedule[4];
			$scope.device.working_schedule.saturday = $scope.device.working_schedule[5];
			$scope.device.working_schedule.sunday = $scope.device.working_schedule[6];
			$scope.device.notification = String(data.notification);
		});

	
	$scope.submitForm = function(){
		var brightnessInfo = parseInt($scope.device.brightness*2.55);
		var contrastInfo = parseInt($scope.device.contrast*2.55);
		var saturationInfo = parseInt($scope.device.saturation*2.55);
		var video_resolutionInfo = $scope.device.video_resolution.split("x");
		var heightInfo = parseInt(video_resolutionInfo[1]);
		var widthInfo = parseInt(video_resolutionInfo[0]);
		var preserve_moviesInfo = parseInt($scope.device.preserve_movies);
		var video_rotationInfo = parseInt($scope.device.video_rotation);

		var working_scheduleInfo = String($scope.device.working_schedule.monday+"|"+$scope.device.working_schedule.tuesday+"|"
			+$scope.device.working_schedule.wednesday+"|"+$scope.device.working_schedule.thursday+"|"
			+$scope.device.working_schedule.friday+"|"+$scope.device.working_schedule.saturday+"|"
			+$scope.device.working_schedule.sunday);
		
		var deviceInfo = {
 		name: $scope.device.name,
 		device_ip: $scope.ip,
 		live_streaming: "http://" + $scope.ip + ":8081",
 		brightness: brightnessInfo,
 		contrast: contrastInfo ,
 		saturation: saturationInfo,
 		height: heightInfo,
 		width: widthInfo,
 		text_left: $scope.device.name,
 		video_rotation: video_rotationInfo,
 		frame_rate: $scope.device.frame_rate,
 		stream_max_rate: $scope.device.stream_max_rate,
 		stream_quality: $scope.device.stream_quality,
 		max_movie_time: $scope.device.max_movie_time,
 		preserve_movies: preserve_moviesInfo,
 		motion_gap: $scope.device.motion_gap,
 		pre_capture: $scope.device.pre_capture,
 		post_capture: $scope.device.post_capture,
 		minimum_motion_frames: $scope.device.minimum_motion_frames,
 		working_schedule: working_scheduleInfo,
 		notification: $scope.device.notification
 		};

 		var wirelessInfo = {
 			ssid: $scope.device.ssid,
 			password: $scope.device.password
 		};

 		console.log(deviceInfo);
		$http({
			method: 'PUT',
			url: 'http://localhost:8080/pimask/edit_picam_device/' + $scope.ip,
			headers: {'Content-Type': 'application/json'},
			data: deviceInfo
		})
		.success(function(data){
			swal({   title: "Device Updated!",   text: data.message,   timer: 5000, type: "success",   showConfirmButton: false });
			$location('/connected_devices')
		})
		.error(function(data){
			swal("Oops!", data.message, "error");
		});
	};
});

app.controller('EditOtherController', function($scope, $http, $routeParams, $location){
	
	$scope.device = {};
	$scope.ip = $routeParams.id;

	$http({
			method: 'GET',
			url: 'http://localhost:8080/pimask/findDevice/' + $scope.ip
		})
		.success(function(data){
			$scope.device = data;
			console.log(data.notification);
			$scope.device.notification = String(data.notification);
		});


	$scope.submitForm = function(){
		var deviceInfo = {
 		name: $scope.device.name,
 		device_ip: $scope.ip,
 		live_streaming: "http://" + $scope.ip + ":8081",
 		notification: $scope.device.notification
 		};

		$http({
			method: 'PUT',
			url: 'http://localhost:8080/pimask/edit_other_device/' + $scope.ip,
			headers: {'Content-Type': 'application/json'},
			data: deviceInfo
		})
		.success(function(data){
			//alert(data.message);
			swal({   title: "Device Updated!",   text: data.message,   timer: 5000, type: "success",   showConfirmButton: false });
			$location('/connected_devices')
		})
		.error(function(data){
			//alert(data.message);
			swal("Oops!", data.message, "error");
		});
	};
});

app.controller('AddUserController', function($scope, $http, $route){

	$scope.newUser = {};

	$http({
			method: 'GET',
			url: 'http://localhost:8080/pimask/users'
		})
		.success(function(data){
			$scope.users = data;
		});

	$scope.addUser = function(){
		
		console.log($scope.newUser);
		$http({
			method: 'POST',
			url: 'http://localhost:8080/pimask/save_user',
			headers: {'Content-Type': 'application/json'},
			data: $scope.newUser
		})
		.success(function(data){
			//alert(data.message);
			swal({   title: "User Created!",   text: data.message,   timer: 2000, type: "success",   showConfirmButton: false });
			$route.reload();
		})
		.error(function(data){
			//alert(data.message);
			swal("Oops!", data.message, "error");
		});

		$scope.newUser = {};
	}

	$scope.deleteUser = function(user){
	
		swal({	title: "Are you sure?",
	 				text: "Device " + name.name + " and all its files will be deleted permanently. Are you sure?",
	 				type: "warning",   showCancelButton: true,   confirmButtonColor: "#DD6B55",
	 				confirmButtonText: "Yes, delete it!",   closeOnConfirm: false },
	 			function(){ 
	 				var URL = 'http://localhost:8080/pimask/delete_user/' + user.user_id;	
					
					$http({
						method: 'DELETE',
						url: URL
					})
					.success(function(data){
						//alert(data.message);
						swal("Deleted!", data.message, "success");
						$route.reload();
					})
					.error(function(data){
						//alert(data.message);
						swal("Oops!", data.message, "error"); 
					});
				});
				$route.reload();
			};
});

app.controller('EditUserController', function($scope, $http, $routeParams, $location){

	$scope.id = $routeParams.id;
	$scope.user = {};
	//console.log($scope.id);

	$http({
			method: 'GET',
			url: 'http://localhost:8080/pimask/users/' + $scope.id
		})
		.success(function(data){
			$scope.user = data;
			$scope.user.notification = String($scope.user.notification);
		});

	$scope.editUser = function(){
		
		//console.log($scope.user);
		$http({
			method: 'PUT',
			url: 'http://localhost:8080/pimask/edit_user/' + $scope.user.user_id,
			headers: {'Content-Type': 'application/json'},
			data: $scope.user
		})
		.success(function(data){
			//alert(data.message);
			swal({   title: "User Updated!",   text: data.message,   timer: 2000, type: "success",   showConfirmButton: false });
			$location.path('/addUser');
		})
		.error(function(data){
			//alert(data.message);
			swal("Oops!", data.message, "error");
		});
	};
});
