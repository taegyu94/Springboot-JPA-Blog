let index = {
	init : function(){
		$("#btn-save").on("click",()=>{	//function(){} 가 아니라 , ()=>{} 를 사용하는 이유는 this 를 바인딩하기 위해서!!
			this.save();
		});
		
		$("#btn-update").on("click",()=>{
			this.update();
		});
		//전통적인 로그인방식
		//$("#btn-login").on("click",()=>{	//function(){} 가 아니라 , ()=>{} 를 사용하는 이유는 this 를 바인딩하기 위해서!!
		//	this.login();
		//});
	},
	
	save : function(){
		//alert('user의 save함수 호출됨');
		let data ={	//자바스크립트 오브젝트
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//console.log(data);
		
		//ajax 호출시 default가 비동기 호출, 따라서 다른 코드들이 실행이 된다.
		//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!!
		//ajax가 통신을 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요.
		$.ajax({
			//통신을 수행해서 회원가입 수행을 요청(100초 가정)
			type:"POST",
			url:"/auth/joinProc",
			data: JSON.stringify(data),	// JSON문자열 , 	http body데이터 >> MIME 타입이 필요
			contentType: "application/json; charset=utf-8",	//body 데이터가 어떤 타입인지(MIME)
			dataType: "json"	//요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 String(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){
			//요청이 정상이면 done 실행
			alert("회원가입이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){
			//요청이 비정상이면 fail을 실행
			alert(JSON.stringify(error));
		});			
	},
	
	update : function(){
		
		let data ={	//자바스크립트 오브젝트
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		$.ajax({
			type:"PUT",
			url:"/user",
			data: JSON.stringify(data),	// JSON문자열 , 	http body데이터 >> MIME 타입이 필요
			contentType: "application/json; charset=utf-8",	//body 데이터가 어떤 타입인지(MIME)
			dataType: "json"	//요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 String(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){
			//요청이 정상이면 done 실행
			alert("회원정보 수정이 완료되었습니다.");
			console.log(resp);
			location.href="/";
		}).fail(function(error){
			//요청이 비정상이면 fail을 실행
			alert(JSON.stringify(error));
		});			
	}
	
} 

index.init();


		/*		전통적인 로그인 방식
		login : function(){
		//alert('user의 save함수 호출됨');
		let data ={	//자바스크립트 오브젝트
			username: $("#username").val(),
			password: $("#password").val(),
		};
		
		//console.log(data);
		
		//ajax 호출시 default가 비동기 호출, 따라서 다른 코드들이 실행이 된다.
		//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!!!
		//ajax가 통신을 성공하고 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요.
		$.ajax({
			//통신을 수행해서 회원가입 수행을 요청(100초 가정)
			type:"POST",
			url:"/api/user/login",
			data: JSON.stringify(data),	// JSON문자열 , 	http body데이터 >> MIME 타입이 필요
			contentType: "application/json; charset=utf-8",	//body 데이터가 어떤 타입인지(MIME)
			dataType: "json"	//요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 String(생긴게 json이라면) => javascript 오브젝트로 변경
		}).done(function(resp){
			//요청이 정상이면 done 실행
			alert("로그인이 완료되었습니다.");
			console.log(resp);
			location.href="/"
		}).fail(function(error){
			//요청이 비정상이면 fail을 실행
			alert(JSON.stringify(error));
		});		
	}*/