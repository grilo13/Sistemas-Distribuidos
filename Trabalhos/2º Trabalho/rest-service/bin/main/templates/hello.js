angular.module('demo', [])
.controller('Hello', function($scope, $http) {
    $http.get('http://localhost:8080/centrosDisponiveis').
        then(function(response) {
            $scope.centros = response.data;
        });
});