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
	.when('/configureDevices', {
		templateUrl: 'templates/configure-devices.html',
		controller: 'ConfigureDevicesController',
		controllerAs: 'configureDevicesCtrl'
	})
	.when('/editDevices', {
		templateUrl: 'templates/edit-devices.html',
		controller: 'EditDevicesController',
		controllerAs: 'editDevicesCtrl'
	})
});

app.controller('FindDevicesController', function($scope, $http){
	$http.get('http://localhost:8080/pimask/find_devices')
	 .success(function(response){
	 	$scope.names = response;
	 })
	 .error(function(){
	 	alert("error");
	 });
});

app.controller('ShowDevicesController', function($scope, $http){
$http.get('http://localhost:8080/pimask/show_devices')
	 .success(function(response){
	 	$scope.names = response;
	 })
	 .error(function(){
	 	alert("error");
	 });
});

app.controller('ConfigureDevicesController', function($scope, $http){
	$http.get('http://localhost:8080/pimask/configure_devices')
	 .success(function(response){
	 	$scope.names = response;
	 })
	 .error(function(){
	 	alert("error");
	 });
});

app.controller('EditDevicesController', function($scope, $http){
	$http.get('http://localhost:8080/pimask/edit_devices')
	 .success(function(response){
	 	$scope.names = response;
	 })
	 .error(function(){
	 	alert("error");
	 });
});
