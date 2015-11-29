
/**
 * models -- interactive data domain specific methods
 */
App.Models.Test = Backbone.Model.extend({
	// set default values of the properties
	defaults : {
		cityname : '',
		retMsg : ''
	},
    url: function(suffix) {
    	return 'upServer'; // get the data url��
    },
    
    initialize: function() {
    	// �󶨴���ļ�����validate����ô˷�����Ҳ������ÿ������ǰ���а�
		this.bind('invalid', function(model, error,arg) {
			alert(error);
		});
    },
    
    validate: function(attrs) {
    	if (attrs.dynasty_id <= 0  ) {
    		return 'dynasty id must be greater than 0';
    	}
    },

    fetch: function (options) {
		var self = this;
		console.log(this.get('cityname'));
		$.post(this.url(), {cityname:this.get('cityname')}, function(data) {
			console.log("success");
			self.set(data);
			options.success();
		},'json');
	},
    
    save: function () {
    	var data = {};
    	for ( var item in this.attributes) {
			var key = 'dynasty.'+item;
			data[key] = this.attributes[item];
		}
    	
		$.post(this.url('save'), data, function(data) {
			
		},'json');
	}
	
});
