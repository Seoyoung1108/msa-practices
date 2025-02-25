import React from 'react';

import Message from './Message';
import * as styles from '../../assets/scss/component/guestbook/MessageList.scss';

export default function MessageList({messages, deleteMessage}) {
    return (
        <ul className={styles.MessageList}>
            {messages.map((m, i) => <Message
                    key={`${m.id}`} 
                    id={m.id}
                    name={m.name}
                    message={m.contents}
                    deleteMessage={deleteMessage} />)
            }
        </ul>
    );
}