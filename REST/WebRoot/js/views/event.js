/**
 * views -- render HTML/CSS with JS templating
 */

App.Views.Event = Backbone.View.extend({
	
	classname : 'hevent_view',
	tagName: 'div',
	template : $('#addevent-template').html(),

	initialize: function(options){
		this.options = options;
		this.bind('change', this.render);
		this.model = this.options.model;
		this.collection = this.options.collection;
	},
	render: function(){ // render������Ŀ��ֻ�����������this.el������this�Ա���ʽ������
		var dynastyJson= {'dynasties':eval(JSON.stringify(this.collection))};
		$(this.el).html(Mustache.to_html(this.template,dynastyJson));
		$("#leftSideBar").html(this.$el);
		
		return this;
	}
});
