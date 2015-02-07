leatherback.directive('selectOnClick', function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            element.on('click', function () {
                this.select();
            });
        }
    };
});

leatherback.directive('nnn', function() {
    return function(scope, element, attrs) {
        var keyCode = [8,9,37,39,48,49,50,51,52,53,54,55,56,57,96,97,98,99,100,101,102,103,104,105,110,190];
         element.bind("keydown", function(event) {
           //console.log($.inArray(event.which,keyCode));
           if($.inArray(event.which,keyCode) == -1) {
               scope.$apply(function(){
                   scope.$eval(attrs.onlyNum);
                   event.preventDefault();
               });
               event.preventDefault();
           }

       });
    };
 });

leatherback.directive('compareTo', function() {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=compareTo"
        },
        link: function(scope, element, attributes, ngModel) {
             
            ngModel.$validators.compareTo = function(modelValue) {
                return modelValue == scope.otherModelValue;
            };
 
            scope.$watch("otherModelValue", function() {
                ngModel.$validate();
            });
        }
    };
});