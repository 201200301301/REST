

function gologin(){
	window.location.href='userLogin.jsp';
}
function goreg(){
	window.location.href="action/jsp/page_goRegister";
}

function focusInput(id){
	var obj = document.getElementById(id);
	obj.style.backgroundColor="rgba(0, 0, 0, 0.3)";
}
function blurInput(id){
	var obj = document.getElementById(id);
	obj.style.backgroundColor="rgba(255, 255, 255, 0.3)";
}

function loginInit(){
	var clientW = window.screen.width;
	var clientH = window.screen.height;
	if(clientW < clientH){
		document.getElementById("loginWrap").style.width = "100%";
	}else{
		document.getElementById("loginWrap").style.width = "33%";
	}
}
function regInit(){
	var clientW = window.screen.width;
	var clientH = window.screen.height;
	if(clientW < clientH){
		document.getElementById("regWrap").style.width = "100%";
	}else{
		document.getElementById("regWrap").style.width = "33%";
	}
}

function doLogin(){
	var userName = $("#userName").val();
	var userPwd = $("#userPass").val();
	console.log(userName+" "+userPwd)
	if(userName=="" || userPwd == ""){
		alert("Please enter the account name and password");
	}else{
		$.post('services/user/check',{"userID":userName,"password":userPwd},function(data) {	
			console.log(data.message)
//			if(data.message == "no"){
//				alert("There is no such account");
//			}else if(data.message == "err"){
//				alert("Wrong Password!");
//			}else{
				window.location.href='index.jsp';
//			}
		},"json");
	}
}

function doReg(){
	var userName = $("#uName").val();
	var userPwd = $("#uPwd").val();

	if(userName == "" || userPwd == ""){
		alert("Please enter the account name and password!");
	}else{
		$.post('action/json/user_register',{"user.user_name":userName,"user.user_pass":userPwd},function(data) {
			if(data.message == "space"){
				alert("Please use English letters, Chinese words or \"_\" to register, do not use space or tab.");
			}else if(data.message == "user_exist"){
				alert("fail creating account, account name may have already exist!");
			}else{
				window.location.href="action/jsp/page_goLogin";
			}
		},"json");
	}
}

function doExit(){
	$.post('action/json/user_exit',{},function(data) {
		if(data.message == "success"){
			window.location.href="/HMapVis_0.2";
		}
	},"json");
}



function editPeople(people_id){
	var editPeopleRouter = new App.Routers.HMapVis();
	
	editPeopleRouter.navigate("editMapEvent/"+event_id,true);
}



function showPage(page,level){
	
	var router = new App.Routers.HMapVis();
	var name="aaaaa";
	if(page == 0){
		console.log("AAAAAAAA");
		router.navigate("hello/"+name, true);
	
	}else if(page == 1){
		console.log("AAAAAAAA");
		router.navigate("world", true);
		
	}else if(page == 2){
		
	}else if(page == 3){
		
	}else if(page == 4){
		
	}
}



function saveRelation(eventid1,eventid2,relationid){
	
		var Revent=new App.Models.EventRela({
			event_id1 : eventid1,
			event_id2 : eventid2,
		    relation_id:relationid 
		});
		Revent.save();
}
