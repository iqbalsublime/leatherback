leatherback.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '/templates/main.tpl.html',
            controller: 'mainCtrl'
        })
        
        .when('/password', {
            templateUrl: '/templates/password.tpl.html',
            controller: 'passwordCtrl'        	
        })
        
        .when('/users', {
            templateUrl: '/templates/user/list.tpl.html',
            controller: 'listUsersCtrl'
        })
        
        .when('/user/:id', {
            templateUrl: '/templates/user/form.tpl.html',
            controller: 'editUserCtrl'
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
        
        .otherwise({
            redirectTo: '/'
        });
});