leatherback.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '/templates/main.tpl.html',
            controller: 'mainCtrl'
        })
        
        .when('/search/byLotNumber', {
            templateUrl: '/templates/search/byLotNumber.tpl.html',
            controller: 'search.byLotNumberCtrl'
        })
        
        .when('/search/byDate', {
            templateUrl: '/templates/search/byDate.tpl.html',
            controller: 'search.byDateCtrl'
        })
        
        .when('/search/byPartNumber', {
            templateUrl: '/templates/search/byPartNumber.tpl.html',
            controller: 'search.byPartNumberCtrl'
        })
        
        .when('/add', {
            templateUrl: '/templates/add.tpl.html',
            controller: 'addCtrl'
        })
        .otherwise({
            redirectTo: '/'
        });
});