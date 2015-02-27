leatherback.service('abstractService', function($http, $q) {
	return ({
		handleError: handleError,
		handleSuccess: handleSuccess
	});
	
	function handleError(response) {
//        if (!angular.isObject(response.data) || !response.data.message) {
//            return($q.reject('An unknown error occurred.'));
//        }
//
//        return($q.reject(response.data.message));
		return($q.reject(response));
    }

    function handleSuccess(response) {
        return(response.data);
    }
});


leatherback.service('userService', function($http, $q, abstractService) {
	return({
		changePassword: changePassword,
		list: list,
		getById: getById,
		update: update
	});
	
	function list() {
		var request = $http({
            method: 'get',
            url: 'api/users'
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	
	function getById(id) {
		var request = $http({
            method: 'get',
            url: 'api/user/' + id
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	
	function changePassword(password) {
		var request = $http({
			method: 'put',
			url: 'api/user/changePassword',
			data: password,
			headers: {
                'Content-Type' : 'application/json; charset=utf-8',
                'Accept' : 'application/json'
			}
		});
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	
	function update(id, user) {
		var request = $http({
            method: 'put',
            url: 'api/user/' + id,
            data: user,
            headers : {
                'Content-Type' : 'application/json; charset=utf-8',
                'Accept' : 'application/json'
            }
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
});

leatherback.service('reportService', function($http, $q, abstractService) {
		return ( {
			query:query,
			exportTo: exportTo
		});
		
		function query(reportQuery, pageIndex) {
			var request = $http({
	            method: 'post',
	            url: 'api/report/query/' + pageIndex,
	            data: reportQuery,
	            headers : {
	                'Content-Type' : 'application/json; charset=utf-8',
	                'Accept' : 'application/json'
	            }
	        });
			
			return(request.then(abstractService.handleSuccess, abstractService.handleError));
		}
		
		function exportTo(reportQuery) {
			var request = $http({
	            method: 'post',
	            url: 'api/report/pdf',
	            data: reportQuery,
	            headers : {
	                'Content-Type' : 'application/json; charset=utf-8',
	                'Accept' : 'application/json'
	            }
	        });
			
			return(request.then(abstractService.handleSuccess, abstractService.handleError));
		}
});

leatherback.service('prescriptionService', function($http, $q, abstractService) {
	return ({
		list: list,
		getById: getById,
		create: create,
		update: update,
		remove: remove,
		findByLotNumber: findByLotNumber,
		findByPartNumber: findByPartNumber
	});
	
	function findByLotNumber(lotNumber, pageIndex) {
		var request = $http({
            method: 'get',
            url: 'api/prescription/search/byLotNumber/' + lotNumber + '/page/' + pageIndex
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	
	function findByPartNumber(partNumber, pageIndex) {
		var request = $http({
            method: 'get',
            url: 'api/prescription/search/byPartNumber/' + partNumber + '/page/' + pageIndex
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	
	function list(pageIndex) {
		var request = $http({
            method: 'get',
            url: 'api/prescription/page/' + pageIndex
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	
	function getById(id) {
		var request = $http({
            method: 'get',
            url: 'api/prescription/' + id
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	
	function create(prescription) {
		var request = $http({
            method: 'post',
            url: 'api/prescription',
            data: prescription,
            headers : {
                'Content-Type' : 'application/json; charset=utf-8',
                'Accept' : 'application/json'
            }
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	
	function update(id, prescription) {
		var request = $http({
            method: 'put',
            url: 'api/prescription/' + id,
            data: prescription,
            headers : {
                'Content-Type' : 'application/json; charset=utf-8',
                'Accept' : 'application/json'
            }
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	
	function remove(id) {
		var request = $http({
            method: 'delete',
            url: 'api/prescription/' + id
        });
		
		return(request.then(abstractService.handleSuccess, abstractService.handleError));
	}
	

});