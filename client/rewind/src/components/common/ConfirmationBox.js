import React from "react";
import { Modal } from "react-bootstrap";
import styled from 'styled-components';
import { deleteTimesheet } from '../../store/slices/timesheets';
import { useDispatch } from 'react-redux';



const IconYes = styled.i`
color: #26bf26;
transition: transform 0.2s;

&:hover{
    color: #2cde2c;
    transform: scale(1.1);
    cursor: pointer;
}
`;

const IconNo = styled.i`
color: #cc1d1f;
transition: transform 0.2s;

&:hover{
    color: #db2325;
    transform: scale(1.1);
    cursor: pointer;
}
`;
export const ConfirmationBox = (props) => {
    const dispatch = useDispatch();

    const handleDispatch = () => {
        props.onHide();
        dispatch(deleteTimesheet({ id: props.timesheetId }));
    }

    return (
        <Modal
            {...props}
            size="xs"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header>
                <Modal.Title style={{ margin: '0 auto' }}>Delete confirmation</Modal.Title>
            </Modal.Header>
            <Modal.Body style={{ display: "flex", height: "100%", justifyContent: "center", alignItems: "center" }}>
                <h5 style={{ display: "flex", justifyContent: "center", alignItems: "center" }}>
                    <p style={{ justifyContent: "center", alignItems: "center", textAlign: 'center' }}>
                        Are you sure you want to <br></br> delete the timesheet for week <br></br> {props.week.week}?
                    </p>
                </h5>
            </Modal.Body>
            <Modal.Footer>
                <div style={{ display: "flex", width: "100%", justifyContent: "space-evenly" }}>
                    <IconYes className="fas fa-check-circle fa-3x" onClick={() => handleDispatch()}></IconYes>
                    <IconNo className="fas fa-times-circle fa-3x" onClick={props.onHide}></IconNo>
                </div>
            </Modal.Footer>
        </Modal>
    );
}