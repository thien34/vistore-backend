import { DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'

interface SpecAttrColsProps {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    onEdit: (record: any) => void
    onDelete: (id: number) => void
}

export const getSpecAttrCols = ({ onEdit, onDelete }: SpecAttrColsProps): TableColumnsType => [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
        align: 'center',
        width: '15%',
        title: 'Display order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        sorter: (a, b) => a.displayOrder - b.displayOrder,
    },
    {
        align: 'center',
        title: 'Number of associated products',
        dataIndex: 'associatedProducts',
        key: 'associatedProducts',
        sorter: (a, b) => a.associatedProducts - b.associatedProducts,
    },
    {
        title: 'Actions',
        key: 'actions',
        width: '20%',
        render: (record) => (
            <>
                <Button type='primary' icon={<EditOutlined />} onClick={() => onEdit(record)}>
                    Edit
                </Button>
                <Button danger icon={<DeleteOutlined />} onClick={() => onDelete(record.id)}>
                    Delete
                </Button>
            </>
        ),
    },
]
