/**
 * views -- render HTML/CSS with JS templating
 */

App.Views.TEST = Backbone.View.extend({

	classname : 'test_view',
	tagName : 'div',
	tmpl_url : '',
	events : {
		"click #search-btn" : "test1",
		"click #search-btn2" : "test2"
	},

	initialize : function(options) {
		console.log("come here sdd");
		this.options = options;
		this.bind('change', this.render);
		this.model = this.options.model;
		this.collection = this.options.collection;
		var self = this;
		$.ajax({
			url : this.options.tmpl_url,
			method : 'GET',
			async : false,
			dataType : 'html',
			success : function(data) {
				self.template = data;
			}
		});
	},
	render : function() {
		console.log("come here");
		$(this.el).html(Mustache.to_html(this.template, this.model.toJSON()));
		$("#test").html(this.$el);
		return this;
	},

	test1 : function() {
		alert("xxxx");
	},
	test2 : function() {
		alert("yyyyy");
	}
	
});
