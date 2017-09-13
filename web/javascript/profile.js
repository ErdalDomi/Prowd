/**
 * Created by e7250 on 7/24/2017.
 */

$(document).ready(function(){


    $('.ui.checkbox').checkbox();
    $('.ui.dropdown')
        .dropdown()
    ;


    function populateDropdown(graphdata){
        $(".ui.dropdown").dropdown({onChange:function(value,text){
            showTable(allEntities, allLabels, allVecs, attrNames, attrComplete, value);
        }});
        $(".ui.dropdown").dropdown('clear');

        var ticks  =[];
        for(var i=0; i<JSON.parse(graphdata).length;i++){
            if(i!=1)
                ticks.push(i+" attributes ");
            else
                ticks.push(i+" attribute");
        }
        console.log(ticks);
        var option = '';
        for (var i=0;i<ticks.length;i++){
            option += '<option value='+[i]+'>' + ticks[i] + '</option>';
        }
        $('#myDropdown').html(option);
    }

    $("#myForm").on("submit", function(event) {

        var data = $("#myForm input[type='radio']:checked").map(function(){return this.value;}).get();
        var dataObj = "";
        for(elem in data){
            console.log("elem is: " + elem);
            dataObj += elem+"="+data[elem]+"&"
            console.log(data[elem]);
        }
        dataObj+="name="+document.getElementsByTagName("h1")[0].id;
        //dataObj = dataObj.substr(0,dataObj.length-1);
        console.log(dataObj);
        $.ajax({
            type: "GET",
            url: "/info?"+dataObj,
            success: function(response){
                document.getElementById("responseTable").innerHTML = "";
                var splitResponse = response.split('|');

                graphData = splitResponse[0];
                populateDropdown(graphData);
                allEntities = splitResponse[1];
                allLabels = splitResponse[2];
                allVecs = splitResponse[3];
                attrNames = splitResponse[4];
                attrComplete = splitResponse[5];
                showChart(graphData);
                //showTable(allEntities, allLabels, allVecs, attrNames, attrComplete, 0);
            }
        });

        event.preventDefault(); // Important! Prevents submitting the form.
    });

    /*
    responseText={
    graphData: [100,200,300],
    tableData: [001,100,010],
    labels : [label1]
    }
     */



    function showTable(allEntities, allLabels, allVecs, attrNames, attrComplete,slot){

        attrNames = attrNames.substring(1,attrNames.length-1).split(",");
        attrNames.unshift("item");
        console.log(attrNames);
        attrComplete = attrComplete.substring(1,attrComplete.length-1).split(",");

        allEntities = allEntities.substring(1,allEntities.length-1); //cleaning []
        var splitEntities = allEntities.split(',');
        var currEntities = splitEntities[slot].split(';');


        allLabels = allLabels.substring(1,allLabels.length-1); //cleaning
        var splitLables = allLabels.split(',');
        var currLabels = splitLables[slot].split(';');


        allVecs = allVecs.substring(1,allVecs.length-1); //cleaning
        var splitVecs = allVecs.split(', ');
        var currVecs = splitVecs[slot].split(';');

        console.log("splitVecs: " + splitVecs);
        console.log("currVecs[0]: " + currVecs[0]);
        if(currVecs[0][0]==' '){
            console.log("there is a space");
            //currVecs[0][0].shift();
        }
        //currVecs = currVecs.substring(1,currVecs.length);


        var table = document.getElementById("responseTable");
        table.innerHTML = ""; //reset table
        var thead = document.createElement('thead');
        table.appendChild(thead);
        var mockAttrArry = ["item","P1", "P2", "P3"];

        for(var i=0;i<attrNames.length;i++){
            var pString = "";
            if(i==0){
                pString = attrNames[i];
            } else {
                pString = attrNames[i]+"("+parseFloat(attrComplete[i-1]).toFixed(2)+")";
            }

            console.log(pString);
            thead.appendChild(document.createElement("th")).
            appendChild(document.createTextNode(pString));
        }
        console.log(currVecs);
        var row   = table.insertRow(0); //
        for(currEnt in currEntities){
            var currRow = table.insertRow(currEnt);
            var itemString = "<a href='"+currEntities[currEnt]+"'>"+currLabels[currEnt]+"</a>";
            var labelsCol = currRow.insertCell(0).innerHTML= itemString;
            var currBinRow = currVecs[currEnt];
            //var attrib1Col = currRow.insertCell(1).innerHTML="1";
            for(currBin in currBinRow){

                currRow.insertCell(parseInt(currBin)+1).innerHTML=currBinRow[currBin];
            }

            //
        }

    }


    function showChart(graphdata){

        var ticks  =[];
        for(var i=0; i<JSON.parse(graphdata).length;i++){
            if(i!=1)
            ticks.push(i+" attributes ");
            else
                ticks.push(i+" attribute");
        }
        console.log(ticks);

        console.log(JSON.parse(graphdata));
        $("#chart").empty(); //reset the graph

        $.jqplot.config.enablePlugins = true;

        var s1 = [500, 600, 7000, 1000, 200, 3000]; //stock data
        // var i=0; //iterator
        // var parsed = JSON.parse(graphdata);
        //
        // for(var slot in parsed){
        //     s1[i] = parsed[slot]; //fill in the data
        //     i++;
        // }
       // var ticks = ['0', '20', '40', '60', '80', '100']; //x axis labels

        plot1 = $.jqplot('chart', [JSON.parse(graphdata)], {
            // Only animate if we're not using excanvas (not in IE 7 or IE 8)..
            //animate: !$.jqplot.use_excanvas,
            seriesDefaults:{
                renderer:$.jqplot.BarRenderer,
                pointLabels: { show: true }
            },
            axes: {
                xaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer,
                    ticks: ticks
                }
            },
            highlighter: { show: false }
        });

    }
});