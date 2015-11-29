/*******************************************************************************
 * ȥ��һ���ַ������˵Ŀո�
 */
function trim(szStr){
  // ȥ���ַ���ͷ���Ŀո�
  while(szStr.length > 0){
    if( szStr.substring(0, 1) != ' '){
      break;
    }else{
      szStr = szStr.substring(1);
    }
  }
  // ȥ���ַ���β���Ŀո�
  while(szStr.length > 0){
    if( szStr.substring(szStr.length - 1, szStr.length) != ' '){
      break;
    }else{
      szStr = szStr.substring(0,szStr.length - 1);
    }
  }
  return szStr;
}
/*******************************************************************************
 * �ж�һ���ַ����Ƿ�Ϊ�Ϸ������ڸ�ʽ��YYYY-MM-DD HH:MM:SS �� YYYY-MM-DD �� HH:MM:SS
 */
function isDateStr(ds){
  parts = ds.split(' ');
  switch(parts.length){
    case 2:
      if(isDatePart( parts[0] ) == true && isTimePart( parts[1] )){
        return true;
      }else{
        return false;
      }
    case 1:
      aPart = parts[0];
      if(aPart.indexOf(':') > 0 ){
        return isTimePart(aPart);
      }else{
        return isDatePart(aPart);
      }
    default:
      return false;
  }
}

/*******************************************************************************
 * �ж�һ���ַ����Ƿ�Ϊ�Ϸ������ڸ�ʽ��YYYY-MM-DD
 */
function isDatePart(dateStr){
  var parts;

  if(dateStr.indexOf("-") > -1){
    parts = dateStr.split('-');
  }else if(dateStr.indexOf("/") > -1){
    parts = dateStr.split('/');
  }else{
    return false;
  }

  if(parts.length < 3){
  // ���ڲ��ֲ�����ȱ���ꡢ�¡����е��κ�һ��
    return false;
  }

  for(i = 0 ;i < 3; i ++){
  // ����������ڵ�ĳ�����ֲ������֣��򷵻�false
    if(isNaN(parts[i])){
      return false;
    }
  }

  y = parts[0];// ��
  m = parts[1];// ��
  d = parts[2];// ��

  if(y > 3000){
    return false;
  }

  if(m < 1 || m > 12){
    return false;
  }
  
  if(d > 31 || d < 1){
	  return false;
  }
  switch(d){
    case 29:
      if(m == 2){
      // �����2�·�
        if( (y / 100) * 100 == y && (y / 400) * 400 != y){
          // �������ܱ�100���������ܱ�400���� (������)
        }else{
          return false;
        }
      }
      break;
    case 30:
      if(m == 2){
      // 2��û��30��
        return false;
      }
      break;
    case 31:
      if(m == 2 || m == 4 || m == 6 || m == 9 || m == 11){
      // 2��4��6��9��11��û��31��
        return false;
      }
      break;
    default:
  }

  return true;
}

/*******************************************************************************
 * �ж�һ���ַ����Ƿ�Ϊ�Ϸ���ʱ���ʽ��HH:MM:SS
 */
function isTimePart(timeStr){
	var parts;
	parts = timeStr.split(':');
	if(parts.length < 2){
		// ���ڲ��ֲ�����ȱ��Сʱ�������е��κ�һ��
		return false;
	}

	for(i = 0 ;i < parts.length; i ++){
	// �������ʱ���ĳ�����ֲ������֣��򷵻�false
		if(isNaN(parts[i])){
			return false;
		}
	}

  h = parts[0];// ��
  m = parts[1];// ��

  if( h < 0 || h > 23){
  // ����Сʱ�ķ�Χ
    return false;
  }
  if( m < 0 || h > 59){
  // ���Ʒ��ӵķ�Χ
    return false;
  }

  if(parts.length > 2){
    s = parts[2];// ��

    if( s < 0 || s > 59){
    // ������ķ�Χ
      return false;
    }
  }

  return true;
}

var b;
function onTimeStartfocus(id){
	var target = document.getElementById(id);
	if(target.value=='��ʼʱ��') {
		target.value='';
	}
	var top = target.offsetTop+35+52;
	var left = target.offsetLeft;
	b = new a(top, left, id);
	// rDrag.init(b.wrapper);
}

function onTimeStartBlur(id){
	var target = document.getElementById(id);
	if (target.value=='') {
		target.value='��ʼʱ��';
	}
}

function onTimeEndfocus(id){
	var target = document.getElementById(id);
	if(target.value=='����ʱ��') {
		target.value='';
	}
	var top = target.offsetTop+35+52;
	var left = target.offsetLeft;
	b = new a(top, left, id);
	// rDrag.init(b.wrapper);
}

function onTimeEndBlur(id){
	var target = document.getElementById(id);
	if (target.value=='') {
		target.value='����ʱ��';
	}
}

var panelChose = 0;
var panelChoseRight = 0;
function checkPanel(sign){
	if(panelChose == 0 || panelChose == 5){
		return true;
	}else if(panelChose != 0 && panelChose != sign && panelChose == 1){
		return false;
	}else if(panelChose != 0 && panelChose != sign && panelChose == 2){
		return false;
	}else if(panelChose != 0 && panelChose != sign && panelChose == 3){
		return false;
	}else if(panelChose != 0 && panelChose != sign && panelChose == 4){
		return false;
	}
}



function display(id){
	var traget=document.getElementById(id);
	if(traget.style.display=="none"){
	traget.style.display="";
	}else{
	traget.style.display="none";
	}
	}

function getparm1() { 
	var url=location.href; 
	var tmp1=url.split("?")[1];
	if (tmp1!=null) {
		var tmp2=tmp1.split("&")[0]; 
		var tmp3=tmp2.split("=")[1]; 
	
		var tmp4=url.split("?")[1]; 
		var tmp5=tmp4.split("&")[1]; 
		var tmp6=tmp5.split("=")[1]; 
	
		var parm1=tmp3;
		var parm2=tmp6; 
		slideBar(parm1,parm2);
	
	 }else 
		 return 0;
} 