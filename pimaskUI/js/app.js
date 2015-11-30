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
	 };
});

app.controller('ConfigureDeviceController', function($scope, $http, $routeParams){
	
	$scope.ip = $routeParams.id; 
	console.log($scope.ip);

	/*$http.get('http://localhost:8080/pimask/configure_devices')
	 .success(function(response){
	 	$scope.names = response;
	 })
	 .error(function(){
	 	alert("error");
	 });*/
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
	};
});
