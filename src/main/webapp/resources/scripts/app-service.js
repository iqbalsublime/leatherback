leatherback.service('prescriptionService', function($http, $q) {
	return ({
		list: list,
		getById: getById,
		create: create,
		update: update,
		remove: remove
	})
	
	function list(pageIndex) {
		var request = $http({
            method: 'get',
            url: 'api/prescription/page/' + pageIndex
        });
		
		return( request.then( handleSuccess, handleError ) );
	}
	
	function getById(id) {
		var request = $http({
            method: 'get',
            url: 'api/prescription/' + id
        });
		
		return( request.then( handleSuccess, handleError ) );
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
		
		return( request.then( handleSuccess, handleError ) );
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
		
		return( request.then( handleSuccess, handleError ) );
	}
	
	function remove(id) {
		var request = $http({
            method: 'delete',
            url: 'api/prescription/' + id
        });
		
		return( request.then( handleSuccess, handleError ) );
	}
	
	function handleError(response) {
        if (
            !angular.isObject(response.data) ||
            !response.data.message
            ) {

            return($q.reject('An unknown error occurred.'));
        }

        return($q.reject(response.data.message));
    }

    function handleSuccess(response) {
        return(response.data);
    }
});