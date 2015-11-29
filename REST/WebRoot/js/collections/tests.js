/**
 * collections -- ordered sets of models
 */

	model : App.Models.Test,
	App.Collections.Tests = Backbone.Collection.extend({
	initialize : function(models, options) {

	},

	url : function(suffix) {
		return 'upServer'; 
	},

	fetch : function(options) {
		var self = this;
		var success = options.success;
		var prefillSuccess = options.prefillSuccess;

		$.post(this.url(), {}, function(data) {
			for ( var i = 0; i < data.length; i++) {
				self.add(data[i]);
			}
			if(success){
				success();
			}
		}, "json");
	}

});