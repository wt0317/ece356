/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

    // page is now ready, initialize the calendar...
    
    (function() {
        // Globals
        var date = new Date(),
            d = date.getDate(),
            m = date.getMonth(),
            y = date.getFullYear(),
            eventList = [];

        var calendar = $('#calendar').fullCalendar({
            allDayDefault: false,
            allDaySlot: false,
            height: window.innerHeight-160,
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            eventStartEditable: false,
            defaultView: 'agendaWeek',
            selectable: true,
            selectHelper: true,
            select: function(start, end, allDay) {
                var title = prompt('Event Title:');
                if (title) {
                    $.get("Appointments", {
                        insert: "true", 
                        title: title, 
                        startTime: dateToDateTime(start), 
                        endTime: dateToDateTime(end)
                    }, 
                    function(data) {
                       alert(data);
                    });
                    calendar.fullCalendar('renderEvent',
                        {
                            title: title,
                            start: start,
                            end: end,
                            allDay: allDay
                        }, 
                        true
                    );
                }
                calendar.fullCalendar('unselect');            
            },
            editable: true,
            titleFormat: {
                month: 'MMMM yyyy',                             // September 2009
                week: "MMMM d[ yyyy]{ '&#8212;'[ MMM] d, yyyy}", // September 7 - 13, 2009
                day: 'dddd, MMM d, yyyy'                  // Tuesday, Sep 8, 2009
            },
            events: []
        });

        function initEvents() {
            $.get("Appointments", {
                select: "true"
            }, function(data){
                try{
                    eventsData = JSON.parse(data).events;
                } catch(e) {
                    console.log(e);
                }
                for(var i=0; i < eventsData.length; i++) {
                    var event = {
                       title: eventsData[i].title,
                       //description: eventsData[i].patient,
                       start: dateTimeToDate(eventsData[i].start),
                       end: dateTimeToDate(eventsData[i].end)
                    };
                    
                    calendar.fullCalendar('renderEvent', event, true);
                }
            });
        }

        function dateToDateTime(date){
            var dateTime = date.getFullYear() + '-' +
                ('00' + (date.getMonth()+1)).slice(-2) + '-' +
                ('00' + date.getDate()).slice(-2) + ' ' + 
                ('00' + date.getHours()).slice(-2) + ':' + 
                ('00' + date.getMinutes()).slice(-2) + ':' + 
                ('00' + date.getSeconds()).slice(-2);
            return dateTime;
        }

        function dateTimeToDate(dt) {
            // Split timestamp into [ Y, M, D, h, m, s ]
            var dt = dt.split(/[- :]/);

            // Apply each element to the Date function
            return new Date(dt[0], dt[1]-1, dt[2], dt[3], dt[4], dt[5]);
        }

        initEvents();
    })();
});
