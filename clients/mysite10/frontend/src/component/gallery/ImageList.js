import React from 'react';
import ImageListItem from './ImageListItem';
import * as styles from '../../assets/scss/component/gallery/ImageList.scss';

export default function ImageList({images, deleteImage}) {
    return (
        <ul className={styles.ImageList}>
            {images?.map((img, idx) => <ImageListItem 
                                            key={idx}
                                            id={img.id}
                                            url={img.image}
                                            comment={img.comment}
                                            deleteImage={deleteImage}/>)}
        </ul>
    )
}