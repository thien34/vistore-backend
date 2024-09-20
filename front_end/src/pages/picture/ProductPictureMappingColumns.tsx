import { Tooltip, Button, TableColumnsType } from 'antd'
import { EditOutlined, DeleteOutlined } from '@ant-design/icons'
import { ProductPictureMappingResponse } from '@/model/ProductPictureMapping'
import AppActions from '@/constants/AppActions'

export const getProductPictureMappingColumns = (
    onEditProductPicture: (record: ProductPictureMappingResponse) => void,
    onDeleteProductPicture: (id: number) => void,
): TableColumnsType<ProductPictureMappingResponse> => [
    {
        title: 'Picture',
        dataIndex: 'pictureUrl',
        key: 'pictureUrl',
        render: (text) => (
            <Tooltip title={text}>
                <a href={text} target='_blank' rel='noopener noreferrer'>
                    <img
                        src={text}
                        alt='Product Picture'
                        style={{
                            width: '100px',
                            height: 'auto',
                            display: 'block',
                            margin: '0 auto',
                            cursor: 'pointer',
                        }}
                    />
                </a>
            </Tooltip>
        ),
        width: '60%',
    },
    {
        title: 'Display Order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder,
        width: '20%',
        ellipsis: true,
    },
    {
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <div style={{ display: 'flex', gap: '8px' }}>
                <Button
                    className='default-btn-color'
                    icon={<EditOutlined />}
                    onClick={() => onEditProductPicture(record)}
                >
                    {AppActions.EDIT}
                </Button>
                <Button
                    className='bg-[#ff4d4f] border-[#ff4d4f] text-white'
                    icon={<DeleteOutlined />}
                    onClick={() => onDeleteProductPicture(record.id)}
                >
                    {AppActions.DELETE}
                </Button>
            </div>
        ),
        width: '20%',
        ellipsis: true,
    },
]
