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
	.when('/', {
		redirectTo:'/connectedDevices'
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

app.controller('ShowDevicesController', function($scope, $http){

	$scope.names = [{"ip": "192.168.100.23", "hostname": "PiCamera"}, {"ip": "192.168.100.27", "hostname": "Android-odqe25242eq3d3"}, {"ip": "192.168.100.35", "hostname": "ASUS-Router"}];
/*$http.get('http://localhost:8080/pimask/show_devices')
	 .success(function(response){
	 	$scope.names = response;
	 })
	 .error(function(){
	 	alert("error");
	 });*/
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
