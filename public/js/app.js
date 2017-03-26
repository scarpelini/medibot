angular.module('Medibot', [])
  .controller('MedibotController', ['MedibotService', '$window', function(MedibotService, $window) {

  	console.log("Medibot Controller  >>>>>");
  	console.log("Medibot Service  >>>>>", MedibotService);
    
    var vm = this;
    var currentContext = null;

    vm.textFieldValue;
    vm.choiceOptions = [
    // 	{"id":"asasasasasasas", "description":"Perda de sensibilidade"}, 
	  	// {"id":"sdsdsdsdsdsdsd", "description":"Inchaço"},
	  	// {"id":"fgfgfgfgfgfgfg", "description":"Formigamento nas mãos"} 
    ];
    
    vm.messages = [
      // {messageClass:"myself-message", message:"Olááááá enfermeeeira!"},
      // {messageClass:"myself-message", message:"Estou com dores no meu coração. Acho que é paixão <3"},
      // {messageClass:"bot-message", message:"Jura? Tenho um ótimo remédio pra você."},
      // {messageClass:"bot-message", message:"VODKA!"},
    ];
    
    vm.chooseOption = function(code, label) 
    {
    	console.log("Option Choice :: ", code);
    	console.log("Option Label :: ", label);

    	showMessage("bot", label);
    	vm.choiceOptions = [];

    	MedibotService.postOptionsChoice(code, currentContext).then(sucessHandler);  
    }

    vm.sendMessage = function(start) 
    {
    	var message = start ? "call" : vm.textFieldValue;

    	if(message == "") return;

      	if(message != "call") showMessage("myself", message);
      	vm.textFieldValue = "";
      	MedibotService.postMessage(message).then(sucessHandler);  
    };

    var sucessHandler = function(e)
    {
		console.log("Success Handler - Response :: ", e);

		var message = e.data.message;

		currentContext = e.data.context;
  		vm.choiceOptions = e.data.symptons;

		showMessage("bot", message);
    }

    function showMessage(type, message, options)
    {
    	var messageType = type+"-message";
    	vm.messages.push({messageClass:messageType, message:message});

    	var messagesContainer = $("#messages-container");
    	var messagesContainerHeight = messagesContainer.height();
    	messagesContainer.scrollTop(messagesContainerHeight-100);
    }

    // start
    vm.sendMessage(true);

  }]).service('MedibotService', ['$http', function($http){
  	
  	var service = this;
  	var production = true;
  	var serviceURL = 'http://10.18.240.81:8080/';

  	var sendMessageURLService = production ? serviceURL : 'data/post_message.json';
  	var sendOptionsURLService = production ? serviceURL : 'data/post_choice.json';

  	service.postMessage = function(message, context)
  	{
  		var data = {
		  "message": message,
		  "context": context
		}

  		return $http.post(sendMessageURLService, data);
  	}

  	service.postOptionsChoice = function(code, context)
  	{
  		var data = {
		  "symptons": code,
		  "context": context
		}

  		return $http.post(sendOptionsURLService, data);
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