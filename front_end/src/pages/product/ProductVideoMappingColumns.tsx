import { Button, TableColumnsType } from 'antd'
import { EditOutlined, DeleteOutlined } from '@ant-design/icons'
import { ProductVideoMappingResponse } from '@/model/ProductVideoMapping'
import AppActions from '@/constants/AppActions '

export const getProductVideoMappingColumns = (
    onEditProductVideo: (record: ProductVideoMappingResponse) => void,
    onDeleteProductVideo: (id: number) => void,
): TableColumnsType<ProductVideoMappingResponse> => [
    {
        title: 'Embed video URL',
        dataIndex: 'videoUrl',
        key: 'videoUrl',
        render: (text) => text ?? 'N/A',
        sorter: (a, b) => a.videoUrl.localeCompare(b.videoUrl),
    },
    {
        width: '10%',
        title: 'Display Order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder,
    },
    {
        width: '20%',
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <div className='flex flex-wrap justify-around'>
                <Button
                    className='bg-[#374151] border-[#374151] text-white m-1'
                    icon={<EditOutlined />}
                    onClick={() => onEditProductVideo(record)}
                >
                    {AppActions.EDIT}
                </Button>
                <Button
                    className='bg-[#ff4d4f] border-[#ff4d4f] text-white m-1'
                    icon={<DeleteOutlined />}
                    onClick={() => onDeleteProductVideo(record.id)}
                >
                    {AppActions.DELETE}
                </Button>
            </div>
        ),
    },
]
