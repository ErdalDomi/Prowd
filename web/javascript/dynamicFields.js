var attCount=2;

function addAtt(e){
    e.preventDefault();
    console.log('inside funct');
    $(this).before('<div class="inline field">'+
        '<label>Attribute</label>'+
        '<input type="text" name="attribute'+attCount+'" placeholder="Attribute '+attCount+'">'+
        '</div>');
    attCount++;
}
$(document).ready(function() {

	$(".valuebutton").click(function(e){ //on add input button click
        e.preventDefault(); //prevent form submission

		currIndex = parseInt(this.id[3]); //getting the current value to be added from the id of the button
		currFacet = parseInt(this.id[1]); //getting which facet we're adding values to

		$(this).before('<div class="inline field">'+
						'<label>Facet value</label>'+
						'<input type="text" name="profile-f'+currFacet+'v'+currIndex+'" placeholder="Facet value '+currIndex+'" id="right">'+
						'</div>');
		//the name of the index reflects which value it is

		currIndex++; //increasing this index will ensure the inserted value is incremented
		$(this).attr('id',(this.id.slice(0,-1) + currIndex)); //this line will replace the last letter with the increased currIndex

    });
	
	$(".facetbutton").click(function(e){
		e.preventDefault();
		currFacet = parseInt(this.id[1]);

		$(this).before('<div class="inline field">' +
				    '<label>Facet </label>'+
				    '<input type="text" name="profile-facet'+currFacet+'" placeholder="Facet '+currFacet+'" id="regularright">'+
				  '</div>'+
					
					'<div id="f'+currFacet+'values">'+
						'<div class="inline field">'+
					    '<label>Facet value</label>'+
					    '<input type="text" name="profile-f'+currFacet+'v1" placeholder="Facet value 1" id="right">'+
					  '</div>'+	
						'<button class="circular ui icon green button valuebutton" id="f'+(currFacet)+'v2" >Add value</button>	'+
					'</div>');
		var buttonElement = document.getElementById('f'+currFacet+'v2');
		buttonElement.onclick=function(e){
			e.preventDefault();

		currIndex = parseInt(this.id[3]); //getting the current value to be added from the id of the button
		currFacet = parseInt(this.id[1]); //getting which facet we're adding values to

		$(this).before('<div class="inline field">'+
					    						'<label>Facet value</label>'+
					    						'<input type="text" name="profile-f'+currFacet+'v'+currIndex+'" placeholder="Facet value '+currIndex+'" id="right">'+
					  							'</div>');
		//the name of the index reflects which value it is
		
		//so far this works for only one facet, need to make generic
		currIndex++;
		$(this).attr('id',(this.id.slice(0,-1) + currIndex)); //this line will replace the last letter with the increased currIndex

		}
		currFacet++;
		$(this).attr('id',(this.id.slice(0,-1) + currFacet));
		
	});



    $(".attributebutton").click(function(e){
    	console.log("inside functionsss");
        e.preventDefault();

        $(this).before('<div class="inline field">'+
            '<label>Attribute</label>'+
            '<input type="text" name="attribute'+attCount+'" placeholder="Attribute '+attCount+'" id="regularright">'+
            '</div>');
        attCount++;
    });

	
});