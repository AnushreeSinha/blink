<html>
<link rel="stylesheet" type="text/css"
	href="js/resources/css/ext-all.css">
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript" src="js/app-all.js"></script>
<jsp:directive.page import="java.io.*" />
<jsp:directive.page import="javax.swing.*" />
<jsp:directive.page import="java.awt.event.*" />
<jsp:directive.page import="java.awt.*" />
<jsp:directive.page import="java.util.*" />
<jsp:directive.page import="javax.swing.SwingUtilities" />
<jsp:directive.page import="javax.swing.filechooser.*" />

<jsp:directive.page import="java.util.*" />



<script>


function loadApplet(code,codebase,width,height){
	var placeholder=document.getElementById('download');
	var a = document.createElement('object'); 
	a.setAttribute('type','application/x-java-applet'); 
	a.setAttribute('width',width); 
	a.setAttribute('height',height); 
	var codeParam = document.createElement('param'); 
	codeParam.setAttribute('name','code');
	codeParam.setAttribute('value',code); 
	a.appendChild(codeParam); 
	var codebaseParam = document.createElement('param'); 
	codebaseParam.setAttribute('name','codebase');
	codebaseParam.setAttribute('value',codebase);
	a.appendChild(codebaseParam);
	placeholder.appendChild(a); }
	
/*function openApplet()
	{alert("hi");
	var app = document.MyApplet;
    var attributes ={code:'MyApplet',width:500, height:300};
	}*/
</script>

<body>
    <applet code="MyApplet.class" codebase="/../main/java/MyApplet.class" height="300" width="550" />
    <!--  <object name="BrowsePage" type="application/x-java-applet" height="300" width="550" > -->
    <param name="code" value="BrowsePage.class"/>
    </object>
	<script>
 var baseURL = 'http://localhost:8080/desginer-web/services/crud/';
	</script>
	<script type="text/javascript" src="js/app-model.js"></script>
	<script type="text/javascript" src="js/app-store.js"></script>
	
	</body>
	

	<script>
	
	   var baseURL = 'http://localhost:8080/desginer-web/services/crud/';
	   var baseGIT='https://localhost:18443/gitblit/repositories/';
	</script>
	<script type="text/javascript" src="js/app-model.js"></script>
	<script type="text/javascript" src="js/app-store.js"></script>
	<script>
	    var sizeIndex;
	    
	    Ext.namespace("Ext.ux");
	    Ext.ux.comboBoxRenderer = function(combo) {
	      return function(value) {
	        var idx = combo.store.find(combo.valueField, value);
	        var rec = combo.store.getAt(idx);
	        return rec.get(combo.displayField);
	      };
	    }



		function createGrid(title, store, columns) {
			  return	Ext.create('Ext.grid.Panel', {
			        title: title,
			        store: store,
			        columns:columns,
			        height: 200,
			        width: 400,
			        name: title,
			        closeable: true,
			        selType: 'rowmodel',
			        plugins: [
			              rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
			                clicksToEdit: 1, 
			                saveBtnText : 'Update New',			                
			            })
			        ],
			       dockedItems: [{
			            xtype: 'toolbar',
			            items: [{
			                text: 'Add',
			                iconCls: 'icon-add',
			                handler: function(){
			                    store.insert(0, new EntityAttribute());
			                    rowEditing.startEdit(0, 0);
			                }
			            }, '-', {
			                text: 'Delete',
			                iconCls: 'icon-delete',
			                handler: function(){
			                    var selection = grid.getView().getSelectionModel().getSelection()[0];
			                    if (selection) {
			                        store.remove(selection);
			                    }
			                }
			            }]
			        }] 
			    });
	    }
		
		function createGrid_attr(title, store, columns, id,entityForm) {
			  return	Ext.create('Ext.grid.Panel', {
			        title: title,
			        store: store,
			        id:title,
			        columns:columns,
			        height: 200,
			        width: 400,
			        name: title,
			        closeable: true,
			        selType: 'rowmodel',
			        plugins: [
			              rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
			                clicksToEdit: 1, 
			                saveBtnText : 'Update New',
			            
			              listeners: {
			            	  click:function(){
			            		  EntityCRUDPanel.hide=true;
			            		  Ext.getCmp('eastPanel').add(AttributeUpdatePanel);
			            	  },
			            	  beforeedit: function(editor,e){
			            		  EntityCRUDPanel.hidden=true;
			            		  AttributeUpdatePanel.hidden=false;
			            		  Ext.getCmp('eastPanel').add(AttributeUpdatePanel);
			            	  },
			                	edit: function(editor, e){
			                	    var record=e.record;
			                	    var valJSON=null;
			        			    var validations = new Array();
			        			    var valId=0;a
			                	    var sizeId=0;
			                	    alert(record.data.toSource());
			                	    if(Ext.getCmp('updateActionAttr') != null){
			                	    record.data.updateActionAttr=Ext.getCmp('updateActionAttr').getValue();
			                	    Ext.getCmp('updateActionAttr').reset();
			                	    }
			                        var a;
			         			   if(record.data.validations.id >= 0){
			         				   valId=record.data.validations.id;
			         				  alert(valId);
			         			   }
			         			   else {
			         				   valId=0;
			         			   }
			         			   for(a=0;a<record.data.validations.length;a++){
			         				validations.push(record.data.validations[a]);
			         			   }
			         			   if(record.data.validations.size != null && record.data.validations.size.id != null){
			         				   sizeId=record.data.validations.id;
			         				   alert(sizeId);
			         			   }
			         			   else {
			         				   sizeId=0;
			         			   }
			         			   
			         				for(a=0;a<validations.length;a++){
			         					if(a>0)
			         						valJSON=valJSON+",";
			         					if(validations[a] == "size") {
			         						if(valJSON!=null)
			         						valJSON+="\"size\":{\"id\":"+sizeId+",\"min\":"+ min+",\"max\":"+max+"}";
			         						else
			         						valJSON="\"size\":{\"id\":"+sizeId+",\"min\":"+ min+",\"max\":"+max+"}";	
			         						
			         					}
			         					else{
			         						if(valJSON!=null)
			         						valJSON+="\""+validations[a]+"\":"+true;
			         						else
			         						valJSON="\""+validations[a]+"\":"+true;	
			         					}
			         				}
			         				record.data.validations="{\"id\" :"+ valId +","+valJSON+"}";	
			         				var ob=JSON.parse(record.data.validations);
			         				record.data.validations=ob;
			            				},
			                
			            	validateedit: function( editor, e, eOpts ){
			            		  AttributeUpdatePanel.hidden=true;
			            		  EntityCRUDPanel.hidden=false;
			            		  Ext.getCmp('eastPanel').add(EntityCRUDPanel);
			            		  
			            	  },
			               				 } 
			              
			            			})
			              ],
			    					
			          dockedItems: [{
			            xtype: 'toolbar',
			            items: [{
			                text: 'Add',
			                iconCls: 'icon-add',
			                handler: function(){
			                    store.insert(0, new EntityAttribute());
			                    rowEditing.startEdit(0, 0);
			                }
			            }, '-', {
			                text: 'Delete',
			                iconCls: 'icon-delete',
			                handler: function(){
			                    var selection = grid.getView().getSelectionModel().getSelection()[0];
			                    if (selection) {
			                        store.remove(selection);
			                    }
			                }
			            }]
			        }] 
			    });
	    }
		var minmaxCount=0;
		function submitCreateAttributeFunction()
		{
			var formPanel = this.up('form');
			var form = formPanel.getForm();
            var formValues = form.getValues();
            formValues.parentPackage  = {'id': form.findField('parentPackage.id').getValue()  };
		    var gridData = new Array();
		   
			for (var j=0; j<=Ext.getCmp('Attributes').getStore().getCount()-1; j++) {
			   Ext.getCmp('Attributes').getSelectionModel().select(j,true);
			   
			   if(Ext.getCmp('Attributes').store.getAt(j).data.dataType == "Primitive") {
				   if(Ext.getCmp('Attributes').store.getAt(j).data.primitiveId >= 0)
					 {
				       var typeid=Ext.getCmp('Attributes').store.getAt(j).data.primitiveId;
				       Ext.getCmp('Attributes').store.getAt(j).data.primitiveId={'id':typeid};
				     } 
				   Ext.getCmp('Attributes').store.getAt(j).data.compositeId=null;
			   }else if(Ext.getCmp('Attributes').store.getAt(j).data.dataType == "Composite"){
				   if(Ext.getCmp('Attributes').store.getAt(j).data.compositeType >= 0)
					 {
				       var typeid=Ext.getCmp('Attributes').store.getAt(j).data.compositeId;
				       Ext.getCmp('Attributes').store.getAt(j).data.compositeId={'id':typeid};
				     } 
				 Ext.getCmp('Attributes').store.getAt(j).data.primitiveId=null;
			   }else{
				   if(Ext.getCmp('Attributes').store.getAt(j).data.primitiveId == "")
					   Ext.getCmp('Attributes').store.getAt(j).data.primitiveId=null;
				   if(Ext.getCmp('Attributes').store.getAt(j).data.compositeId == "")
					   Ext.getCmp('Attributes').store.getAt(j).data.compositeId=null;
				   
			   }
			    if(Ext.getCmp('Attributes').getSelectionModel().isSelected(j)){
			    	if(Ext.getCmp('Attributes').store.getAt(j).data.id > 0)
		               gridData.push(Ext.getCmp('Attributes').store.getAt(j).data);
			    	else{
			    		Ext.getCmp('Attributes').store.getAt(j).data.id=0;
			    		gridData.push(Ext.getCmp('Attributes').store.getAt(j).data);
			    	}
			    		
		      }
			   
			                }
			formValues.entityAttributes = gridData;
			 if(Ext.getCmp('createAction') != null){
	        	   formValues.createAction=Ext.getCmp('createAction').getValue();
               	   Ext.getCmp('createAction').reset();
               	    }
			 if(Ext.getCmp('readAction') != null){
	        	   formValues.readAction=Ext.getCmp('readAction').getValue();
	        	   Ext.getCmp('readAction').reset();
			 }
			 if(Ext.getCmp('updateAction') != null){
	        	   formValues.updateAction=Ext.getCmp('updateAction').getValue();
	        	   Ext.getCmp('updateAction').reset();
	         }
			 if(Ext.getCmp('deleteAction') != null){
	        	   formValues.deleteAction=Ext.getCmp('deleteAction').getValue();
	        	   Ext.getCmp('updateAction').reset();
			 }
			    
	return formValues; 

		}
	  		
	  		
		function getEntityFormItems() {
	
			return [ { name : 'id', xtype: 'hiddenfield',allowBlank : false, value : 0},
			         { fieldLabel : 'Name', name : 'name', allowBlank : false},
			         { fieldLabel : 'Description', name : 'description', allowBlank : false},
				     Ext.create('Ext.form.ComboBox', {
					    fieldLabel : 'Package',
					    name : 'parentPackage.id',
					    store : packageStore,
					    queryMode : 'local',
					    displayField : 'name',
					    valueField : 'id'
				    } )];
		};

		var ctxMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : 'Add Attribute',
				action : 'addattribute'
			}, {
				text : 'Edit',
				action : 'edit'
			}, {
				text : 'Delete',
				action : 'delete'
			} ]
		});

		var containerCtxMenu = Ext.create('Ext.menu.Menu', {
			items : [ {
				text : 'New Package',
				action : 'newpackage',
				handler : clickHandler
			}, {
				text : 'New Entity',
				action : 'addentity',
				handler : clickHandler
			}, {
				text : 'Manage Type',
				action : 'managetype',
				handler : clickHandler
			}, {
				text : 'Add Type',
				action : 'addtype',
				handler : clickHandler
			}, {
				text : 'Refresh',
				action : 'refresh'
			} ]
		});

		var treePanel = Ext.create('Ext.tree.Panel', {
			contextMenu : ctxMenu,
			title : 'Entity Explorer',
			width : 200,
			height : 150,
			store : store,
			rootVisible : false,
			region : 'west',
			listeners : {
				itemclick : function(view, rec, item, index, eventObj) {
					if (rec.raw.leaf == true){
						modifyEntityForm(rec.raw.id);
	     				Ext.getCmp('eastPanel').add(EntityCRUDPanel);
					}
					else
						createPackageForm();
				}
			},
			columns : [ {
				xtype : 'treecolumn',
				header : 'Packages',
				dataIndex : 'text',
				flex : 1
			} ]
		});

		var tabs = Ext.create('Ext.tab.Panel', {
			width : 900,
			height : 400,
			id:'tabPanel',
			region:'center'
		});
		
		/* tabs.on('beforeremove', function(tabs, tab) {
			EntityCRUDPanel.hidden=true;
            var idx = tabs.items.indexOf(tab) - 1;
            setTimeout(function() {
                console.log(idx);
                tabs.setActiveTab(idx);
            }, 350);

        }); */
		
		var resultsPanel = Ext.create('Ext.panel.Panel', {
		    height: 30,
		    region: 'north'
		});
		
		var eastPanel = Ext.create('Ext.form.Panel', {
		    title : 'East Panel',
			region : 'east',
			id:'eastPanel',
			renderTo:Ext.getBody(),
			split : true,
			width : 200,
			height : 150,
	        anchor  :'100%',
			layout: {
			    type:  "vbox",
			    pack: "center"
			},
			align : 'center',
			pack: 'center'
		
	});
		Ext.onReady(function() {
			EntityCRUDPanel.add(createBtn,
			{xtype:'splitter'
	        },
	        readBtn,
	        {
	            xtype:'splitter'
	        },
	        updateBtn,
	        {
	            xtype:'splitter'
	           },
	        deleteBtn);
		});
        
		Ext.onReady(function() {
			AttributeUpdatePanel.add(UpdateAttributeBtn);		
		});
		
		
		var win ;
		var minAppWin; 
		var miniApp;
		function createMiniApp( response ) {
			 miniApp = Ext.JSON.decode(response.responseText);
			
			if( response.status == "200")
			   win.hide();
			
			var items = new Array();
			
			if ( miniApp.db ) {
				items.push({
		            xtype: 'fieldset',
		            columnWidth: 0.5,
		            title: 'DB Config',
		            collapsible: true,
		            defaultType: 'textfield',
		            defaults: {
		                anchor: '100%'
		            },
		            layout: 'anchor',
		            items: [{name:'jdbcDriver',fieldLabel:'Driver Name'},
		                    {name:'jdbcURL',fieldLabel:'JDBC URL:'},
		                    {name:'username',fieldLabel:'Username:'},
		                    {name:'password',fieldLabel:'Password:'}
		                   ] 
		            });
			} 
			
			if( miniApp.persistenceAPI ) {
				items.push({
		            xtype: 'fieldset',
		            columnWidth: 0.5,
		            title: 'Persistence API Config',
		            collapsible: true,
		            defaultType: 'textfield',
		            defaults: {
		                anchor: '100%'
		            },
		            layout: 'anchor',
		            items: [{name:'inheritence',fieldLabel:'Inheritence'},
		                    {name:'idgeneration',fieldLabel:'Id Generation'},
		                    {name:'association',fieldLabel:'Association'}
		                   ] 
		            });
			}
			
			if( miniApp.securityAPI ) {
				
			}
			
			if( miniApp.securityStore) {
				if( miniApp.securityStore == "LDAP" ) {
					
					items.push({
			            xtype: 'fieldset',
			            columnWidth: 0.5,
			            title: 'LDAP Config',
			            collapsible: true,
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%'
			            },
			            layout: 'anchor',
			            items: [{name:'userProvider',fieldLabel:'Provider'},
			                    {name:'userFilter',fieldLabel:'Filter'},
			                    {name:'authzIdentity',fieldLabel:'Identity'}
			                   ] 
			            });
				} else if ( miniApp.securityStore =="RDBMS") {
					
				} else if ( miniApp.securityStore =="OPENID") {
					
				} else if (miniApp.securityStore == "FACEBOOK" ) {
					
					items.push({
			            xtype: 'fieldset',
			            columnWidth: 0.5,
			            title: 'FaceBook Config',
			            collapsible: true,
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%'
			            },
			            layout: 'anchor',
			            items: [{name:'facebook.clientId',fieldLabel:'ClientId'},
			                    {name:'facebook.clientSecret',fieldLabel:'ClientSecret'}
			                   ] 
			            });
				};
			}
			
			if( miniApp.frontend) {
				
			}
			minAppWin =  Ext.create('widget.window', {
                title: 'Package Selection',
                closable: true,
                closeAction: 'hide',
                width: 650,
                height: 550,
                layout: 'border',
                bodyStyle: 'padding: 5px;',
                items: [{
                	xtype: 'form',
                	width : 650,
                    region: 'west',
                    title: 'Small - Single Tier App Config',
                    collapsible: true,
                    floatable: false,
                    layout: 'form',
                    defaultType: 'textfield',
                    items:  items,
                    buttons: [{
                        text: 'Reset',
                        handler: function() {
                            this.up('form').getForm().reset();
                        }
                    }, {
                        text: 'Create',
                        formBind: true, 
                        disabled: true,
                        handler: function() {
                            var form = this.up('form').getForm();
							var formData=form.getValues();
							alert(formData.toSource());
							formData.app={'id':miniApp.id};
							var a=form.findField('jdbcURL').getValue();
							var b=form.findField('jdbcDriver').getValue();
							var c=form.findField('username').getValue();
							var d=form.findField('password').getValue();
							formData.dbConfig={'id':0, 'jdbcURL':a, 'jdbcDriver':b, 'username':c, 'password':d, 'name':miniApp.db};
							var e=form.findField('inheritence').getValue();
							var f=form.findField('association').getValue();
							var g=form.findField('idgeneration').getValue();
							formData.persistenceAPIConfig={'id':0, 'inheritence':e, 'association':f, 'idgeneration':g, 'name':miniApp.persistenceAPI};
							formData.securityAPIConfig={'id':0, name:miniApp.securityAPI};
							if(miniApp.securityStore=="LDAP"){
								var h=form.findField('userProvider').getValue();
								var i=form.findField('userFilter').getValue();
								var j=form.findField('authzIdentity').getValue();
								formData.securityStoreConfig={'lDAPConfig':{'id':0, 'userProvider':h, 'userFilter':i, 'authzIdentity':j, name:miniApp.securityStore}, 'id':0, name:miniApp.securityStore};
							}
							
							else if(miniApp.securityStore=="Facebook"){
								var h=formData.findField('clientId').getValue();
								var i=formData.findField('clientSecret').getValue();
								
								formData.securityStoreConfig={'facebookConfig':{'id':0, 'clientId':h, 'clientSecret':i, name:miniApp.securityStore}, 'id':0, name:miniApp.securityStore};
								
								
								
							}
							formData.name=miniApp.name;
							formData.description=miniApp.description;
							formData.id=0;
							Ext.Ajax.request({
		        				url : baseURL + 'AppConfig/',
		        				method : 'POST',
		        				headers : {
		        					'Content-Type' : 'application/json'
		        				},
		        				jsonData : formData,
		        				success :function() {alert("Yipee!! Success!");
		        				
		        					Ext.Ajax.request({
		        						url: baseURL+'autogen/app/small/'+miniApp.id,
		        						method: 'POST',
		        						headers : {
				        					'Content-Type' : 'application/json'
				        				},
				        				timeout:600000,
				        				success:function() {
				        				alert("App made success");
				        				
				        				Ext.getCmp('download').show();
				        			
				        				},
				        				failure : function(){ alert("App not made");
				        				 }
				        				});
				        				},
		        					
		        				failure : function(){ alert("Failed");
		        				 window.location.reload();}
		        				
		        				
		        			});
							
							
                           
                        }
                    
                    
                    
                    }]
                    
                }]
			});
			
			minAppWin.show(viewport,  function () {});
			
		}
		
	
			win =  Ext.create('widget.window', {
                title: 'Download',
                closable: true,
                closeAction: 'hide',
                id:'download',
                resizable: true,
                draggable: true,
                //animateTarget: this,
                width: 100,
                height: 100,
                layout: 'border',
                bodyStyle: 'padding: 5px;',
             
                	buttons: [{
                        text: 'Download',
                        handler: function() {
                        	var test=window.open();
                        	test.location='http://localhost:8080/desginer-web/download?fileName='+miniApp.name;

                        }
                }]
            	
			});
		
			
		function newApp (item, e) {
			win =  Ext.create('widget.window', {
	                title: 'App Selection',
	                closable: true,
	                closeAction: 'hide',
	                resizable: true,
	                draggable: true,
	                //animateTarget: this,
	                width: 650,
	                height: 550,
	                layout: 'border',
	                bodyStyle: 'padding: 5px;',
	                items: [{
	                	xtype: 'form',
	                    region: 'west',
	                    title: 'Small - Single Tier',
	                    width: 200,
	                    split: true,
	                    collapsible: true,
	                    floatable: false,
	                    layout: 'form',
	                    defaultType: 'textfield',
	                    items: [
	                            {
	                            	name:'name',
	                            	fieldLabel:'App Name'
	                            },
	                            {
	                            	name: 'description',
	                            	fieldLabel: 'Description'
	                            },
	                            {
	                            	name:'basePackage',
	                            	fieldLabel:'Base Package'
	                            	
	                            },
	                    	Ext.create('Ext.form.ComboBox', {
	                    		name :'db',
	                    	    fieldLabel: 'DB Type',
	                    	    store: dbTypes,
	                    	    queryMode: 'local',
	                    	    displayField: 'name',
	                    	    valueField: 'abbr',

	                    	}), 
	                    	Ext.create('Ext.form.ComboBox', {
	                    		name: 'persistenceAPI',
	                    	    fieldLabel: 'Persistence API',
	                    	    store: persistenceAPITypes,
	                    	    queryMode: 'local',
	                    	    displayField: 'name',
	                    	    valueField: 'abbr',

	                    	}),
	                        Ext.create('Ext.form.ComboBox', {
	                        	name: 'securityAPI',
	                    	    fieldLabel: 'Security API',
	                    	    store: securityAPITypes,
	                    	    queryMode: 'local',
	                    	    displayField: 'name',
	                    	    valueField: 'abbr',

	                    	}),
	                        Ext.create('Ext.form.ComboBox', {
	                        	    name:'securityStore',
		                    	    fieldLabel: 'Security Store',
		                    	    store: securityStoreTypes,
		                    	    queryMode: 'local',
		                    	    displayField: 'name',
		                    	    valueField: 'abbr',

		                    }),
		                    Ext.create('Ext.form.ComboBox', {
		                    	name:'frontend',
	                    	    fieldLabel: 'Frontend',
	                    	    store: frontendTypes,
	                    	    queryMode: 'local',
	                    	    displayField: 'name',
	                    	    valueField: 'abbr',
	                        })
		                    
	                    	],
	                    buttons: [{
	                        text: 'Reset',
	                        handler: function() {
	                            this.up('form').getForm().reset();
	                        }
	                    }, {
	                        text: 'Create',
	                        formBind: true, 
	                        disabled: true,
	                        handler: function() {
	                            var form = this.up('form').getForm();

	                            if (form.isValid()) {
	                            	ajaxRequest(form, 'app', form.getValues(),createMiniApp);
	                            }
	                        }
	                    }]
	                }, {
	                    region: 'center',
	                    title: 'Medium - 3 Tier',
	                    width: 200,
	                    split: true,
	                    collapsible: true,
	                    floatable: false
	                },{
	                    region: 'east',
	                    title: 'Large - Multi Tier',
	                    width: 200,
	                    split: true,
	                    collapsible: true,
	                    floatable: false
	                }]
	            });
			win.show(this,  function () {});
		}
		var menu = Ext.create('Ext.menu.Menu', {
	        items: [               
	            {text: 'App' , handler: newApp },{text: 'Package', handler: newPackage}, {text: 'Entity', handler: newEntity },{text: 'Page'},{text: 'Menu'},'-', 
	            {
	                text: 'Config',
	                menu: {        
	                    items: [
	                        '<b class="menu-title">Config Category</b>',
	                        {
	                            text: 'Persistence',
	                            group: 'theme'
	                        }, {
	                            text: 'Event Broadcasting',
	                            checked: false,
	                            group: 'theme'
	                        }, {
	                            text: 'Security',
	                            menu: {
	                            	items: [
	                            	        { text: 'SSO' }, {text: 'Social'} ,{ text: 'Local DB'}, { text: 'Local LDAP'}
	                            	       ]
	                            }
	                        }, {
	                            text: 'Cache',
	                            checked: false,
	                            group: 'theme'
	                        },
	                        {
	                            text: 'Validation',
	                            checked: false,
	                            group: 'theme'
	                        },
	                        {
	                            text: 'Frontend',
	                            checked: false,
	                            group: 'theme'
	                        }
	                    ]
	                }
	           }
	        ]
	    }); 
		
		Ext.onReady(function() {
		    resultsPanel.addDocked({ xtype: 'toolbar',
                 dock: 'bottom',
                 items: [{text: 'File' },{
                     text:'New',
                     iconCls: 'bmenu', 
                     menu: menu  
                 }]
		    });
		});
		
		var createBtn =  new Ext.Button({
	        text    : 'Create',
	        height  : 30,
	        width   : 100,
	        x       : 30,
	        handler : function() {
	        	CRUDCall('Code for Create','createAction');
	        }
	    });
		var readBtn =  new Ext.Button({
	        text    : 'Read',
	        width   : 100,
	        height  : 30,
	        x       : 30,
	        handler : function() {
	        	CRUDCall('Code for Read','readAction');
	        }
	    });
		var updateBtn =  new Ext.Button({
	        text    : 'Update',
	        width   : 100,
	        height  : 30,
	        x       : 30,
	        handler : function() {
	        	CRUDCall('Code for update','updateAction');
	        }
	    });
		var deleteBtn =  new Ext.Button({
	        text    : 'Delete',
	        width   : 100,
	        height  : 30,
	        x       : 30,
	        handler : function() {
	        	CRUDCall('Code for delete','deleteAction');
	        }
	    });
		var UpdateAttributeBtn =  new Ext.Button({
	        text    : 'Update',
	        height  : 30,
	        width   : 100,
	        x       : 30,
	        handler : function() {
	        	CRUDCall('Code when Attribute is updated','updateActionAttr');
	        }
	    });
		
		var viewport = new Ext.Viewport({
			title : 'BorderLayout Demo',
			layout : 'border',
 			items : [ resultsPanel, treePanel, tabs, eastPanel, {
				xtype : 'panel',
				title : 'South Panel',
				region : 'south',
				split : true,
				collapsible : true,
				collapseMode : 'mini',
				height : 150
			} ]
		});

		function addToViewPort(panel) {
			tabs.setActiveTab(tabs.add(panel));
			
		}
		function clickHandler(item, e, eOpts) {
			if (item.action == "newpackage")
				createPackageForm();
			else if (item.action == "managetype")
				manageTypeForm();
			else if (item.action == "addtype")
				addNewType();
			else if (item.action == "addentity")
				addNewEntity();
		};

		function addNewEntity() {
			var entityNewForm = createForm('Add Entity', 'entity/',getEntityFormItems(), submitCreateEntityFunction);

			addToViewPort(entityNewForm);
		}

		
		function addNewType() {
			var items = [{ name : 'id', xtype: 'hiddenfield',allowBlank : false, value : 0},
			   {
				fieldLabel : 'Name',
				name : 'name',
				allowBlank : false
			}, {
				fieldLabel : 'Description',
				name : 'description',
				allowBlank : false
			}, {
				fieldLabel : 'Class Name',
				name : 'className',
				allowBlank : false
			} ];
			var typeForm = createForm('Add Type', 'type/', items);

			addToViewPort(typeForm);
		}

		function manageTypeForm() {
			var columns = [ {
				text : 'Name',
				dataIndex : 'name'
			}, {
				text : 'Class Name',
				dataIndex : 'className',
				flex : 1
			}, {
				text : 'Description',
				dataIndex : 'description'
			} ];

			var typeGrid = createGrid('Manage Types', typeStore, columns);
			addToViewPort(typeGrid);

		}

		function createPackageForm() {
			var items = [ {
				fieldLabel : 'Name',
				name : 'name',
				allowBlank : false
			}, {
				fieldLabel : 'Description',
				name : 'description',
				allowBlank : false
			} ];
			var packageForm = createForm('Add Package', 'package/', items);
			// viewport.getComponent(1).add(packageForm);

			addToViewPort(packageForm);
		}
        
		var isDisabled1=false;
		var isDisabled2=false;
		var data = null ;
		var tabPanel=0;
		function modifyEntityForm(id) {
            
			var entityForm = createForm('Modify Entity'+tabPanel, 'entity/', getEntityFormItems(),submitCreateAttributeFunction);
			tabPanel++;
			var columns = [ { text: 'id', dataIndex: 'id', hidden:true,  editor: 'textfield'},
			               {text : 'Name',dataIndex : 'name',editor: 'textfield'}, 
			               {text : 'Description',dataIndex : 'description',editor: 'textfield'},
			             {text : 'Is Searchable',dataIndex : 'searchable',editor:new Ext.form.field.ComboBox({
                               typeAhead:true,
                               triggerAction:'all',
                               store:optionStore,
                               editable:true,
                               selectOnTab:true,
                               valueField: 'name',
                               displayField: 'abbr'
                               
                               
                            	})},
                            {text : 'DataType',dataIndex :'dataType',editor: new Ext.form.field.ComboBox({
                                    typeAhead:true,
                                    triggerAction:'all',
                                    store:selectStore,
                                    editable:true,
                                    selectOnTab:true,
                                    displayField: 'name', 
                                    valueField: 'name',
                                    listConfig: {
                                      listeners: {
                                    	 itemclick: function(list, record, combo) {
                                    	    var combo1 = Ext.getCmp('Comp');
                                    	    var combo2 = Ext.getCmp('Prim');
                                    	           if(record.get('name')=="Primitive"){
                                                     combo1.disable();
                                                     combo2.enable();
                                    	}
                                    	else{        combo2.disable();
                                    	             combo1.enable();
                                    	}}
}}

                                    
                                })}, 
                            	
                            	
                            	
			               {text : 'Primitive',dataIndex : 'primitiveId',editor: new Ext.form.field.ComboBox({
                               typeAhead:true,
                               triggerAction:'all',
                               editable:true,
                               selectOnTab:true,
                               store:typeStore,
					    		name:'id',
                               id:'Prim',
                               displayField: 'name', 
                               valueField: 'id',
                               queryMode : 'local',
                               lastQuery : '',
                               forceSelection: true,
                               renderer: function(value) {
                            	   
                           	  	if (!isNaN(value)){
                           	  		
                           	  		if (Ext.data.StoreManager.lookup('typeStoreId').findRecord('id', value) != null) 
                           	  			return Ext.data.StoreManager.lookup('typeStoreId').findRecord('id', value).get('name');
                           	  		else
                           	  			return value;   
                           	  			 	  		
                           	  	}
                           	  	
                           	  	else if (typeof value != 'undefined') {
                           	  		if (value.name != null)
                           	  			return value.name;
                           	  		else
                           	  			return "";
                           	  	}
                           	  	
                           	  	else
                           	  		return "";


                           	  }
                            	
                                      
                               
                           })}, 
                           {text : 'Composite' ,dataIndex : 'compositeId',editor: new Ext.form.field.ComboBox({
                               typeAhead:true,
                               triggerAction:'all',
                               editable:true,
                               selectOnTab:true,
                               store:entityStore,
                               id:'Comp',
                               displayField: 'name', 
                               valueField: 'id',
                               queryMode : 'local',
                               lastQuery : ''
                               
                            	
                                      
                               
                           })},
                           {text : 'MultiType',dataIndex : 'multiType',editor: new Ext.form.field.ComboBox({
                               typeAhead:true,
                               triggerAction:'all',
                               editable:true,
                               selectOnTab:true,
                               store:collectionStore,
                               displayField: 'abbr', 
                               valueField: 'name',
                               queryMode : 'local',
                               lastQuery : ''
                               
                            	
                                      
                               
                           })},
                           {text : 'Is Primary Key',dataIndex : 'primaryKey',editor:new Ext.form.field.ComboBox({
                               typeAhead:true,
                               triggerAction:'all',
                               store:optionStore,
                               editable:true,
                               selectOnTab:true,
                               valueField: 'name',
                               displayField: 'abbr'
                               
                               
                            	})},
                           {text : 'Is Required',dataIndex : 'isRequired',editor: new Ext.form.field.ComboBox({
                               typeAhead:true,
                               triggerAction:'all',
                               editable:true,
                               selectOnTab:true,
                               store:optionStore,
                               displayField: 'abbr', 
                               valueField: 'name',
                               queryMode : 'local',
                               lastQuery : ''
                               
                            	
                                      
                               
                           })},
                          {text : 'Validations',dataIndex : 'validations',editor: new Ext.form.field.ComboBox({
                        	  typeAhead:true,
                              triggerAction:'all',
                              editable:true,
                              selectOnTab:true,
                              store:validStore,
                              displayField: 'name', 
                              valueField: 'abbr',
                              queryMode : 'local',
                              multiSelect: true,
                              listeners: {
                                 'select' : function(field,value) {
                                     if(field.getValue() == "size"){
                                			 sizeWin.show();
                                	 }
                              }
                              }
                        	  
                          })}
                           
                            	
                            	];
			
 
			Ext.Ajax.request({
				url : baseURL + 'entity/' + id,
				method : 'GET',
				reader: {            
	                  type:'json',
	                  root: 'entity'
	        },
				success : function(response) {
					data = Ext.JSON.decode(response.responseText.trim());
					var form = entityForm.getForm();
					form.findField('id').setValue(data.id);
					form.findField('name').setValue(data.name);
					form.findField('description').setValue(data.description);
				    form.findField('parentPackage.id').setValue(data.parentPackage.id);
					var attributeStore = Ext.create('Ext.data.JsonStore', {
				        model: 'EntityAttribute',
				        data: data.entityAttributes
				        
				    });
				
					
					var attributeGrid = createGrid_attr('Attributes', attributeStore, columns, id,entityForm); 

					entityForm.add(attributeGrid);
					addToViewPort(entityForm);
					
				}
			});
		}
		
		function CRUDCall(title,id){
		 var CRUDWindow=Ext.create('widget.window', {
            title: title,
            closable: true,
            closeAction: 'hide',
            resizable: true,
            draggable: true,
            width: 550,
            height: 250,
            layout: 'anchor',
            bodyStyle: 'padding: 5px;',
            items: [{
            	 xtype: 'textareafield',
                 grow: true,
                 name: id,
                 id:id,
                 fieldLabel: 'Code',
                 anchor: '100%'
            }
               ],
                buttons : [{
					text : 'Reset',
					handler : function() {
						Ext.getCmp(id).reset();
					}
				}, {
					text : 'Submit',
					handler : function(){
						this.up('.window').close();
					}
					
            }]
            });
		 CRUDWindow.show();
		 if(id == 'createAction')
		    Ext.getCmp('createAction').setValue(data.createAction);
		 else if(id == 'readAction')
		    Ext.getCmp('readAction').setValue(data.readAction);
		 else if(id == 'updateAction')  
		    Ext.getCmp('updateAction').setValue(data.updateAction);
		 else  if(id == 'deleteAction') 
		    Ext.getCmp('deleteAction').setValue(data.deleteAction);
		}
		
		var entityNum=0;
		
		var EntityCRUDPanel = Ext.create('Ext.form.Panel', {
			region : 'east',
			id:'entityCrudPanel'+ (entityNum++),
			autoShow:false,
			width : 150,
			height : 180,
			border : false,
	        anchor  :'100%',
			layout: {
			    type:  "vbox",
			    pack: "center"
			},
			align : 'center',
			pack: 'center'
		
	});
		var AttributeUpdatePanel = Ext.create('Ext.form.Panel', {
			region : 'east',
			id:'attributeUpdatePanel',
			autoShow:false,
			width : 150,
			height : 180,
			border : false,
	        anchor  :'100%',
			layout: {
			    type:  "vbox",
			    pack: "center"
			},
			align : 'center',
			pack: 'center'
		
	});
        
		function createForm(title, resource, items ,submitFunction ) {
			
			return Ext.create('Ext.form.Panel', {
				title : title,
				id : title,
				bodyPadding : 5,
				width : 350,
				layout : 'anchor',
				closable : true,
				waitMsgTarget : true,
				defaults : {
					anchor : '100%'
				},
				defaultType : 'textfield',
				items : items,
				listeners: {
                    /*' close' : function(tabs, tab) {
                        var idx = tabs.items.indexOf(tab) - 1;
                        setTimeout(function() {
                            console.log(idx);
                            tabs.setActiveTab(idx);
                        }, 350);

                    } */
                   'beforeclose' : function( panel, eOpts ){
                	   alert("hi");
                	   var idx = tabs.items.indexOf(panel) - 1;
                       setTimeout(function() {
                           console.log(idx);
                           tabs.setActiveTab(idx);
                       }, 350);
                   }
                 }, 
				buttons : [ {
					text : 'Reset',
					handler : function() {
						this.up('form').getForm().reset();
					}
				}, {
					text : 'Submit',
					formBind : true,
					disabled : true,
					preSubmit : submitFunction,
					handler : function() {
						var formValues = null;

						var form = this.up('form').getForm();
						if( this.preSubmit != undefined ) {
							formValues  = this.preSubmit();
						}else {
							formValues = form.getValues();
						}
						if (form.isValid()) {
							ajaxRequest(form, resource, formValues,shandler);
						}
						
					} 
				}
				]
			});
		}
		function shandler()
		{
			//Ext.Msg.alert(Success);
			window.location.reload();
		}

		function submitCreateEntityFunction() {
			var formPanel = this.up('form');
			var form = formPanel.getForm();
            
			var formValues = form.getValues();
			
		  	//alert(formValues.toSource()); 
			
			formValues.parentPackage  = {'id': form.findField('parentPackage.id').getValue()  };
			var gridRecords = formPanel.items.items[3].getStore().data.items;
			
			var gridData = new Array();
			for(var i =0; i < gridRecords.length ; i++)  {
			      gridData.push(gridRecords[i].data);
		    }
			//formValues.entityAttributes = gridData;
			alert(formValues.toSource());
		    return formValues; 
		}
		
		function ajaxRequest(form, resource , formValues, successHandler) {
           if( successHandler == undefined) {
        	   successHandler = function (response) {
        		   Ext.Msg.alert('Success', response.status);
        	   };
           }
			
			Ext.Ajax.request({
				url : baseURL + resource,
				method : 'POST',
				headers : {
					'Content-Type' : 'application/json'
				},
				jsonData : formValues,
				success : successHandler,
					
					
			   // function(response) {
					//Ext.Msg.alert('Success', response.status);
					//var jsonData = Ext.util.JSON.decode(response.responseText);
					//alert(jsonData.id);
					//alert(response.status);
				//},
				failure : function(form, action) {
					Ext.Msg.alert('Failed', 'Server Error');
				}
			});
		}
        var min;
        var max;
		var sizeWin=Ext.create('widget.window', {
            title: 'Size parameters',
            closable: true,
            closeAction: 'hide',
            resizable: true,
            draggable: true,
            width: 350,
            height: 250,
            layout: 'anchor',
            bodyStyle: 'padding: 5px;',
            items: [{
                xtype: 'textfield',
                id:'min',
                fieldLabel:'min'},
                {
               	 xtype: 'textfield',
                 id:'max',
                 fieldLabel:'max'
               	 
                }],
                buttons : [{
					text : 'Reset',
					handler : function() {
						Ext.getCmp('min').reset();
						Ext.getCmp('max').reset();
					}
				}, {
					text : 'Submit',
					handler : function(){
						min=Ext.getCmp('min').value;
						max=Ext.getCmp('max').value;
						this.up('.window').close();
					}
					
            }]
            });
		treePanel.on('itemcontextmenu', function(view, record, item, index, event) {
			ctxMenu.showAt(event.getXY());
			event.stopEvent();
		}, this);

		treePanel.on('containercontextmenu', function(view, event, t) {
			containerCtxMenu.showAt(event.getXY());
			event.stopEvent();
		}, this);
		
		function newEntity(item, e) {
			var entityNewForm = createForm('Add Entity', 'entity/',getEntityFormItems(), submitCreateEntityFunction);

			addToViewPort(entityNewForm);
		}
		
		function newPackage(item, e){
			createPackageForm();
			
		}
				
	</script>
</body>
</html>
