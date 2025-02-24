import React from 'react';
import * as styles from '../../assets/scss/component/guestbook/Message.scss';

export default function Message({id, name, message, deleteMessage}) {
    return (
        <li 
            key={`ssqsqqsqsqs${id}    `}
            className={styles.Message}>
            <strong>{name}</strong>
            <p>
                {
                    message?.split('\n').map((line, i) => i > 0 ?
                        <span key={`${id}${i}`}>
                            <br/>
                            {line}
                        </span> :
                        <span key={`${id}${i}`}>
                            {line}
                        </span>
                    )
                }
            </p>
            <strong/>
            <a onClick={() => deleteMessage(id)}>삭제</a>
        </li>
    );
}