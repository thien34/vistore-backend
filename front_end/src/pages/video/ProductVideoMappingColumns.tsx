import { Button, TableColumnsType } from 'antd'
import { EditOutlined, DeleteOutlined } from '@ant-design/icons'
import { ProductVideoMappingResponse } from '@/model/ProductVideoMapping'
import defaultThumbnail from '../picture/video-not-found.jpg'
import AppActions from '@/constants/AppActions'

export const getProductVideoMappingColumns = (
    onEditProductVideo: (record: ProductVideoMappingResponse) => void,
    onDeleteProductVideo: (id: number) => void,
): TableColumnsType<ProductVideoMappingResponse> => [
    {
        title: 'Preview',
        dataIndex: 'videoUrl',
        key: 'preview',
        render: (text) => {
            let thumbnailUrl = ''

            if (text.includes('cloudinary.com')) {
                thumbnailUrl = text.replace('/video/upload/', '/video/upload/').replace('.mp4', '.jpg')
            } else if (text.includes('youtube.com') || text.includes('youtu.be')) {
                const videoId = text.split('v=')[1]?.split('&')[0] || text.split('/').pop()
                thumbnailUrl = `https://img.youtube.com/vi/${videoId}/0.jpg`
            } else {
                thumbnailUrl = defaultThumbnail
            }

            return (
                <a href={text} target='_blank' rel='noopener noreferrer'>
                    <img
                        src={thumbnailUrl}
                        style={{ width: '100px', height: 'auto', display: 'block', margin: '0 auto' }}
                    />
                </a>
            )
        },
        width: 120,
    },
    {
        title: 'Embed video URL',
        dataIndex: 'videoUrl',
        key: 'videoUrl',
        render: (text) =>
            text ? (
                <a
                    href={text}
                    target='_blank'
                    rel='noopener noreferrer'
                    style={{
                        display: 'block',
                        maxWidth: '100%',
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        whiteSpace: 'nowrap',
                    }}
                >
                    {text}
                </a>
            ) : (
                'N/A'
            ),
        sorter: (a, b) => a.videoUrl?.localeCompare(b.videoUrl ?? '') ?? 0,
        width: 250,
        ellipsis: true,
    },
    {
        width: 100,
        title: 'Display Order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder,
        ellipsis: true,
    },
    {
        width: 160,
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <div style={{ display: 'flex', gap: '8px' }}>
                {' '}
                <Button
                    className='default-btn-color'
                    icon={<EditOutlined />}
                    onClick={() => onEditProductVideo(record)}
                >
                    {AppActions.EDIT}
                </Button>
                <Button
                    className='bg-[#ff4d4f] border-[#ff4d4f] text-white'
                    icon={<DeleteOutlined />}
                    onClick={() => onDeleteProductVideo(record.id)}
                >
                    {AppActions.DELETE}
                </Button>
            </div>
        ),
        ellipsis: true,
    },
]
