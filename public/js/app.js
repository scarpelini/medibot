angular.module('Medibot', [])
  .controller('MedibotController', ['MedibotService', function(MedibotService) {

  	console.log("Medibot Controller  >>>>>");
  	console.log("Medibot Service  >>>>>", MedibotService);
    
    var vm = this;

    vm.textFieldValue;
    
    vm.messages = [
      {messageClass:"myself-message", message:"Olááááá enfermeeeira!"},
      {messageClass:"myself-message", message:"Estou com dores no meu coração. Acho que é paixão <3"},
      {messageClass:"bot-message", message:"Jura? Tenho um ótimo remédio pra você."},
      {messageClass:"bot-message", message:"VODKA!"},
    ];
    
    vm.chooseOption = function(code) 
    {
    	MedibotService.postOptionsChoice(code).then(function(e){
    		console.log("Sended Option Choice - Response :: ", e);

    		vm.messages.push({messageClass:"bot-message", message:message});
    	});  
    }

    vm.sendMessage = function() 
    {
    	var message = vm.textFieldValue;

    	console.log("vm.textFieldValue :: ", message);
    	// return;

      	showMessage("myself", message);
     //  	MedibotService.postMessage(message).then(function(e){

     //  		var response = "Resposta de marcação.";
     //  		var options = {};

     //  		showMessage("bot", message, options);
    	// 	console.log("Sended Message - Response :: ", e);
    	// });  
    };

    function showMessage(type, message, options)
    {
    	var messageType = type+"-message";
    	vm.messages.push({messageClass:messageType, message:message});
    }

  }]).service('MedibotService', ['$http', function($http){
  	var service = this;

  	service.postMessage = function(message)
  	{
  		return $http.post('data/post_message.json', message, config).then(successMessageHandler, errorMessageHandler);
  	}

  	service.postOptionsChoice = function(code)
  	{
  		return $http.post('data/post_choice.json', code, config).then(successMessageHandler, errorMessageHandler);
  	}

  	function successMessageHandler(e)
  	{
  		console.log("Success Response ::: ", e);
  	}

  	function errorMessageHandler(e)
  	{
  		console.log("Error :: ", e);
  	}

  }]);