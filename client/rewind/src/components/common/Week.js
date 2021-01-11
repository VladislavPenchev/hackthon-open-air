import React from "react";
import {useSelector} from 'react-redux';
import moment from 'moment';

export const Week = ({ week }) => {
    // const timesheets = useSelector(state => state.timesheets.timesheets.content);
    // const isAlreadyCreated = (week) => {
    //     if(
    //     timesheets.some((el) => {
    //         console.log(week);
    //       if(moment(el.from).format("DD/MM/YYYY") === week.split(" - ")[0]){
    //           return true;
    //       }
    //       return false;
    //     })){
    //         return true;
    //     }
    //     return false;
    // }
    return (
        <option value={week}>{week}</option>
    )
}