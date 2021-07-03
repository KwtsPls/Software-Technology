import React, { Component, useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom'
import {
    MonthlyBody,
    MonthlyCalendar,
    MonthlyNav,
    DefaultMonthlyEventItem,
} from '@zach.codes/react-calendar';

export default MyMonthlyCalendar = () => {
    let [currentMonth, setCurrentMonth] = useState<Date>(
    new Date()
    );

    return (
    <MonthlyCalendar
        currentMonth={currentMonth}
        onCurrentMonthChange={date => setCurrentMonth(date)}
    >
        <MonthlyNav />
        <MonthlyBody
        events={[
            { title: 'Call John', date: new Date() },
            { title: 'Call John', date: new Date() },
            { title: 'Meeting with Bob', date: new Date() },
        ]}
        renderDay={data =>
            data.map((item, index) => (
            <DefaultMonthlyEventItem
                key={index}
                title={item.title}
                date={item.date}
            />
            ))
        }
        />
    </MonthlyCalendar>
    );
};