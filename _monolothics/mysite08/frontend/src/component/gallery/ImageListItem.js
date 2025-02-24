import React from 'react';
import * as styles from '../../assets/scss/component/gallery/ImageListItem.scss';

export default function ImageListItem({id, url, comment, deleteImage}) {
    return (
        <li className={styles.ImageListItem}>
            <span style={{backgroundImage: `url(${url})`}}/>
            <a onClick={() => deleteImage(id)}>삭제</a>
        </li>
    )
}