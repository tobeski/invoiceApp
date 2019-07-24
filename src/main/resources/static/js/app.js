var app = angular.module('invoicingApp', ['angularSpinner', 'ngFileSaver']);

app.controller('mainCtrl', function ($scope, $http, usSpinnerService, FileSaver, Blob, InvoicingService) {
	
	$scope.file = {
		fileName: ""
	}
	
	$scope.uploadFile = function(){
		usSpinnerService.spin('upload-spinner');
		var formData = new FormData($("#fileUpload")[0]);
		InvoicingService.uploadFile(formData).then(function(data){
			$scope.file.fileName = data.data.resultString;
			usSpinnerService.stop('upload-spinner');
		}, function(error){
			console.log("An error occurred");
			usSpinnerService.stop('upload-spinner');
		})
		
	}
	
	$scope.openFile = function(fileName){
		window.open("http://localhost:8811/export/downloadFile?fileName="+fileName);
	}
	
	function str2bytes (str) {
		var bytes = new Uint8Array(str.length);
		for (var i=0; i<str.length; i++) {
			bytes[i] = str.charCodeAt(i);
		}
		return bytes;
	}
	
});


app.factory("InvoicingService", function($http){
	var obj = this;
	obj.uploadFile = function (data) {
        var req = {
            headers: {'Content-Type': undefined},
            method: "POST",
            data: data,
            url: "http://localhost:8811/export/"
        };

        return $http(req);
    };
	
	return obj;
})