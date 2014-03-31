/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

    // page is now ready, initialize the calendar...
    // Variables from jsp
    // selectable
    
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
            height: window.innerHeight-140,
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            eventStartEditable: false,
            defaultView: 'agendaWeek',
            selectable: selectable,
            selectHelper: true,
            select: function(start, end, allDay, jsEvent, view) {
                if(view.name === 'month') {
                    return;
                }
                var modal = $('#myModal');
                modal.modal('show');
                
                $('#addAppointment').off('click');
                $('#addAppointment').on('click', function () {
                    loadBtn();

                    var title = $('#appTitle').val();
                    var patient = $('#appPatient option:selected').html();
                    var patientID = $('#appPatient').val();
                    var event = {
                        insert: "true", 
                        title: title, 
                        start: dateToDateTime(start), 
                        end: dateToDateTime(end),
                        patient: patient,
                        patientID: patientID,
                        doctor: appointmentDoctor,
                        doctorID: appointmentDoctorID,
                        creator: appointmentCreator
                    };
                    $.get("Appointments", event, function(data) {
                        if(data === 'Successfully added.') {
                            calendar.fullCalendar('renderEvent', event, true );
                        } else {
                            alert(data);
                        }
                        calendar.fullCalendar('unselect');
                        resetBtn();
                    });       
                    $(this).off('click');
                });
            },
            editable: true,
            titleFormat: {
                month: 'MMMM yyyy',                             // September 2009
                week: "MMMM d[ yyyy]{ '&#8212;'[ MMM] d, yyyy}", // September 7 - 13, 2009
                day: 'dddd, MMM d, yyyy'                  // Tuesday, Sep 8, 2009
            },
            dayClick: function(date, allDay, jsEvent, view) {
                console.log('Current view: ' + view.name);
                console.log('Current date: ' + date);
                if(view.name === 'month') {
                    calendar.fullCalendar( 'changeView', 'agendaWeek').fullCalendar('gotoDate', date);
                }
            },
            events: [],
            eventClick: function(calEvent, jsEvent, view) {
                if(view.name === 'month' || !removeable){
                    return;
                }
                var eventModal = $('#eventModal');
                    eventModal.modal('show');
                $('#event-modal-title').html(calEvent.title);
                $('#event-modal-time').val(
                    calEvent.start.toDateString()
                    + ", "
                    + (calEvent.start.getHours() < 10?'0':'') + calEvent.start.getHours()
                    + ":"
                    + (calEvent.start.getMinutes() < 10?'0':'') + calEvent.start.getMinutes()
                    + " - " 
                    + (calEvent.end.getHours() < 10?'0':'') + calEvent.end.getHours()
                    + ":" 
                    + (calEvent.end.getMinutes() < 10?'0':'') + calEvent.end.getMinutes()
                );
                $('#event-modal-patient').val(calEvent.patient);
                $('#event-modal-doctor').val(calEvent.doctor);
                $('#event-modal-staff').val(calEvent.creator);
                
                $('#deleteAppointment').off('click');
                $('#deleteAppointment').on('click', function () {
                    loadBtn();
                    $.get("Appointments", {
                        delete: "true", 
                        startTime: dateToDateTime(calEvent.start), 
                        doctor: calEvent.doctorID
                    }, 
                    function(data) {
                        if(data !== 'Successfully removed.') {
                            alert(data);
                        }
                        resetBtn();
                    });
                    calendar.fullCalendar('removeEvents',calEvent._id);
                    $(this).off('click');
                });
                
                $(this).css('border-color', 'red');
                $(this).css('border-color', '#3a87ad');

            }
        });
        addButton();

        function initEvents() {
            loadBtn();
            $.get("Appointments", {
                select: "true",
                doctor: appointmentDoctorID
           }, function(data){
                try{
                    eventsData = JSON.parse(data).events;
                } catch(e) {
                    console.log(e);
                    resetBtn();
                }
                for(var i=0; i < eventsData.length; i++) {
                    var event = {
                       title: eventsData[i].title,
                       patient: eventsData[i].patient,
                       start: dateTimeToDate(eventsData[i].start),
                       end: dateTimeToDate(eventsData[i].end),
                       creator: eventsData[i].creator,
                       doctor: eventsData[i].doctor,
                       doctorID: eventsData[i].doctorID,
                       allDay: false
                    };
                    calendar.fullCalendar('renderEvent', event, true);
                }
                resetBtn();
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
        
        function addButton() {
            var btn = '<button type="button" id="load-btn" data-loading-text="Loading..." class="btn btn-primary">Done</button>';
            $('.fc-header-left').append(btn);
        }
        
        function loadBtn() {
            $('#load-btn').removeClass('hide').button('loading');
        }
                
        function resetBtn() {
            $('#load-btn').button('reset').addClass('hide');
        }
        
        initEvents();
        window.addEventListener("beforeunload", function( event ) {
            loadBtn();
            $('#overlayMask').modal('show').append(btn);
        });
    })();
});
