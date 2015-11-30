var app = angular.module('PiMask',['ngRoute'])
.config(function($routeProvider){
	$routeProvider.when('/connectedDevices', {
		templateUrl: '/templates/connected-devices.html',
		controller: 'ShowDevicesController',
		controllerAs:'showDevicesCtrl'
	})
	.when('/findDevices', {
		templateUrl: '/templates/find-devices.html',
		controller: 'FindDevicesController',
		controllerAs:'findDevicesCtrl'
	})
	.when('/configureDevice/:id', {
		templateUrl: 'templates/configure-devices.html',
		controller: 'ConfigureDeviceController',
		controllerAs: 'configureDeviceCtrl'
	})
	.when('/editDevice', {
		templateUrl: 'templates/edit-devices.html',
		controller: 'EditDeviceController',
		controllerAs: 'editDevicesCtrl'
	})
	.when('/addUser', {
		templateUrl: 'templates/add-user.html',
		controller: 'AddUserController',
		controllerAs: 'AddUserCtrl'
	})
	.when('/editUser/:id', {
		templateUrl: 'templates/edit-user.html',
		controller: 'EditUserController',
		controllerAs: 'EditUserCtrl'
	})
	.when('/', {
		redirectTo:'/connectedDevices'
	})
});

app.controller('FindDevicesController', function($scope, $http){
	
	$scope.names = [{"ip": "192.168.100.23", "hostname": "PiCamera"}, {"ip": "192.168.100.27", "hostname": "Android-odqe25242eq3d3"}, {"ip": "192.168.100.35", "hostname": "ASUS-Router"}];
	
	/*$http.get('http://localhost:8080/pimask/find_devices')
	 .success(function(response){
	 	$scope.names = response;
	 })
	 .error(function(){
	 	alert("Unable to find any devices");
	 });*/
});

app.controller('ShowDevicesController', function($scope, $http, $route, $routeParams){

	//$scope.names = [{"ip": "192.168.100.23", "hostname": "PiCamera"}, {"ip": "192.168.100.27", "hostname": "Android-odqe25242eq3d3"}, {"ip": "192.168.100.35", "hostname": "ASUS-Router"}];
	$scope.names = {};

	$http.get('http://localhost:8080/pimask/connected')
	 .success(function(response){
	 	$scope.names = response;
	 	if(response.length == 0)
	 	{
	 		alert("No devices conncted to the server.");
	 	}
	 })
	 .error(function(response){
	 	alert("error");
	 });

	 $scope.deleteDevice = function(name){

	 	var x;
		
		if(confirm("Device " + name.name + " and all its files will be deleted permanently. Are you sure?") == true){
			var URL = 'http://localhost:8080/pimask/deleteDevice/' + name.device_id;	
			$http({
				method: 'DELETE',
				url: URL
			})
			.success(function(data){
				alert(data.message);
				$route.reload();
			})
			.error(function(data){
				alert(data.message);
			});
		} else{

			$route.reload();
		}
	 };
});

app.controller('ConfigureDeviceController', function($scope, $http, $routeParams){
	
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
		alert(working_scheduleInfo);
		
		var deviceInfo = {
 		name: $scope.device.name,
 		device_ip: $routeParams.id,
 		brightness: brightnessInfo,
 		contrast: contrastInfo ,
 		saturation: saturationInfo,
 		height: heightInfo,
 		width: widthInfo,
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
 		working_schedule: working_scheduleInfo
 		};

 		var wirelessInfo = {
 			ssid: $scope.device.ssid,
 			passwors: $scope.device.password
 		};

		console.log(deviceInfo);
	};

});

app.controller('EditDeviceController', function($scope, $http){
	
	/*$http.get('http://localhost:8080/pimask/edit_devices')
	 .success(function(response){
	 	$scope.names = response;
	 })
	 .error(function(){
	 	alert("error");
	 });*/
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
			alert(data.message);
			$route.reload();
		})
		.error(function(data){
			alert(data.message);
		});

		$scope.newUser = {};
	}

	$scope.deleteUser = function(user){

		var x;
		
		if(confirm("User " + user.email + " will be deleted. Are you sure?") == true){

			var URL = 'http://localhost:8080/pimask/delete_user/' + user.user_id;	
			$http({
				method: 'DELETE',
				url: URL
			})
			.success(function(data){
				alert(data.message);
				$route.reload();
			})
			.error(function(data){
				alert(data.message);
			});
		} else {

			$route.reload();
		}
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
			
			if(data.notification == true)
			{
				$scope.user.notification = "true";
			} else
			{
				$scope.user.notification = "false";
			}
			//console.log($scope.user);
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
			alert(data.message);
			$location.path('/addUser');
		})
		.error(function(data){
			alert(data.message);
		});
	};
});
