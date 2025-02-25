import React, {useEffect, useState, useRef} from 'react';
import Modal from "react-modal";
import axios from 'axios';
import update from 'react-addons-update';
import WriteForm from './WriteForm';
import MessageList from './MessageList';
import * as styles from '../../assets/scss/component/guestbook/Guestbook.scss';
import * as modalStyles from "../../assets/scss/component/modal/modal.scss";

Modal.setAppElement(document.getElementById("root"));

export default function Guestbook() {
    let isFetching = false;

    const [modalData, setModalData] = useState({open: false, id: 0, password: '', errorMessage: null});
    const [messages, _setMessages] = useState([]);
    
    const messagesRef = useRef(messages);
    const refDeleteForm = useRef(null);

    useEffect(() => {
        const handleWindowScroll = function() {
            const documentHeight = window.document.body.offsetHeight;
            const viewportHeight = document.documentElement.clientHeight || window.innerHeight;
            const scrollTop = document.documentElement.scrollTop;
            if (viewportHeight + scrollTop + 10 > documentHeight) {
                fetchMessages.call(this);
            }
        }

        window.addEventListener('scroll', handleWindowScroll);
        fetchMessages.call(this);

        return () => window.removeEventListener('scroll', handleWindowScroll);
    }, []);

    useEffect(() => {
        setTimeout(() => {
            refDeleteForm.current && refDeleteForm.current.password.focus();
        }, 200);
    }, [modalData]);    

    const setMessages = (messages) => {
        messagesRef.current = messages;
        _setMessages(messages);
    }

    const addMessage = async function(message) {
        try {
            const response = await axios.post(API_URL_GUESTBOOK, message);
            const jsonResult = response.data;
            setMessages([jsonResult.data, ...messages]);
        } catch(err) {
            console.error(err.response ? `${err.response.status} ${err.response.data.message}` : err);
        }                
    }

    const deleteMessage = function(id) {
        setModalData({open: true, id: id, errorMessage: '', password: ''});
    };

    const fetchMessages = async function() {
        try {
            if (isFetching) {
                return;
            }

            isFetching = true;

            const messagesInState = this ? messagesRef.current : messages;
            const startId = messagesInState.length === 0 ? 0 : messagesInState[messagesInState.length - 1].id;

            const response = await axios.get(`${API_URL_GUESTBOOK}?id=${startId}`);
            const jsonResult = response.data;

            jsonResult.data.length > 0 && setMessages([...messagesInState, ...jsonResult.data]);
            isFetching = false;
        } catch (err) {
            console.error(err.response ? `${err.response.status} ${err.response.data.message}` : err);
        }
    }

    return (
        <>
            <div className={styles.Guestbook}>
                <h2>방명록</h2>
                <WriteForm addMessage={addMessage}/>
                <MessageList messages={messages} deleteMessage={deleteMessage}/>
            </div>
            <Modal
                isOpen={modalData.open}
                shouldCloseOnOverlayClick={false}
                className={modalStyles.Modal}
                overlayClassName={modalStyles.Overlay}
                style={{content: {width: 350}}}>
                <h1>방명록 삭제</h1>
                <div>
                    <form
                        ref={refDeleteForm}
                        className={styles.DeleteForm}
                        onSubmit={async (e) => {
                            try {
                                const response = await fetch(`${API_URL_GUESTBOOK}/${e.target.id.value}`, {
                                    method: 'delete',
                                    headers: {
                                        'Accept': 'application/json',
                                        'Content-Type': 'application/x-www-form-urlencoded'
                                    },
                                    body: `password=${e.target.password.value}`
                                });

                                if (!response.ok) {
                                    throw new Error(`${response.status} ${response.statusText}`);
                                }

                                const jsonResult = await response.json();
                                if (jsonResult.result !== 'success') {
                                    throw jsonResult.message;
                                }

                                if(!jsonResult.data) {
                                    setModalData(update(modalData, {password: {$set: ''}, errorMessage: {$set: '비밀번호가 틀립니다.'}}));
                                    return;
                                }

                                setMessages(messages.filter(message => message.id != jsonResult.data));
                                setModalData({open: false, id: 0, password: '', errorMessage: null});
                            } catch (err) {
                                console.error(err);
                            }
                        }}>
                        <input type={'hidden'} name={'id'} value={modalData.id}/>
                        <input
                            type={'password'}
                            name={'password'}
                            placeholder={'비밀번호'}
                            value={modalData.password}
                            autoComplete={'off'}
                            onChange={(e) => setModalData(update(modalData, {password: {$set: e.target.value}}))}
                            autoFocus={true} />
                        <p>{modalData.errorMessage}</p>
                    </form>
                </div>
                <div className={modalStyles['modal-dialog-buttons']}>
                    <button onClick={() => refDeleteForm.current.dispatchEvent(new Event("submit", {cancelable: true, bubbles: true}))}>확인</button>
                    <button onClick={() => setModalData({open: false, id: 0, password: '', errorMessage: null})}>취소</button>
                </div>
            </Modal>
        </>
    );
}