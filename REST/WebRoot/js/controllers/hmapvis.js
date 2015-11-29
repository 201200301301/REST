/**
 * router -- methods for routing URL fragments
 */
var Edit_event_id;
var user_image_layer = null;
App.Routers.HMapVis = Backbone.Router
		.extend({
			routes : {
				"hello/:name" : "hello",// 测试
				"hello2/dynasty=:did/name=:en" : "hello2",
				"world" : "world",// 测试
				
			
			},

			home : function() {
				
			},

			initialize : function() {
				this.cached = {
					view : undefined,
					model : undefined,
					collection : undefined
				}
			},

			hello : function(name) {
				
				var fetchCity = new App.Models.Test({
					cityname : name
				});
				fetchCity.fetch({
					success : function(model) {
						console.log("yyyyy");
						var helloView = new App.Views.TEST({
							model : fetchCity,
							tmpl_url : 'js/templates/test_template.html'
						});
						helloView.trigger('change');
					}
				});
			},
			hello2 : function(did, en) {
				helloModel.fetch({
					success : function(model) {
						var helloView = new App.Views.Hello({
							model : model
						});
						helloView.trigger('change');
					}
				});
			},

			world : function() {
			var tests = new App.Collections.Tests();
			tests.fetch({
				success : function() {
					var dynastyListView = null;
			
						dynastyListView = new App.Views.DynastyList({
							collection : tests,
							tmpl_url : 'js/templates/testCollection_template.html'
						});
					dynastyListView.trigger('change');
				}
			});
			},
		
	
		});