leatherback.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '/templates/main.tpl.html',
            controller: 'mainCtrl'
        })
        
        .when('/report', {
            templateUrl: '/templates/report.tpl.html',
            controller: 'reportCtrl'
        })
        
        .when('/add', {
            templateUrl: '/templates/add.tpl.html',
            controller: 'addCtrl'
        })
        
        .when('/edit/:id', {
            templateUrl: '/templates/edit.tpl.html',
            controller: 'editCtrl'
        })
        
        .when('/:id', {
            templateUrl: '/templates/show.tpl.html',
            controller: 'showCtrl'
        })
        
        .when('/changePassword', {
            templateUrl: '/templates/changePassword.tpl.html',
            controller: 'changePasswordCtrl'        	
        })
        
        .otherwise({
            redirectTo: '/'
        });
});