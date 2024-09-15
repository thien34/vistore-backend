import { DeleteOutlined, EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { ProductAttributeResponse } from '@/model/ProductAttribute.ts'
import { Link } from 'react-router-dom'
import ManagerPath from '@/constants/ManagerPath'
import { PredefinedProductAttributeValueRequest } from '@/model/PredefinedProductAttributeValue'

export const getProductAttributeColumns = (): TableColumnsType<ProductAttributeResponse> => [
    {
        width: '50%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
        width: '40%',
        title: 'Description',
        dataIndex: 'description',
        key: 'description',
        sorter: (a, b) => a.description.localeCompare(b.description),
    },
    {
        width: '10%',
        title: 'Action',
        align: 'center',
        key: 'action',
        render: (_, record) => (
            <Link to={`${ManagerPath.PRODUCT_ATTRIBUTE_UPDATE.replace(':id', String(record?.id))}`}>
                <Button className='default-btn-color' icon={<EditOutlined />}>
                    Update
                </Button>
            </Link>
        ),
    },
]

export const getProductAttributeValueColumns = (
    handleEditValue: (name: PredefinedProductAttributeValueRequest) => void,
    handleRemoveValue: (record: PredefinedProductAttributeValueRequest) => void,
): TableColumnsType<PredefinedProductAttributeValueRequest> => [
    { title: 'Name', dataIndex: 'name', key: 'name' },
    {
        title: 'Price Adjustment',
        dataIndex: 'priceAdjustment',
        key: 'priceAdjustment',
        render: (text, record) => `${text} ${record.priceAdjustmentUsePercentage ? '%' : '$'}`,
    },
    { title: 'Weight Adjustment', dataIndex: 'weightAdjustment', key: 'weightAdjustment' },
    {
        align: 'center',
        title: 'Pre-selected',
        dataIndex: 'isPreSelected',
        key: 'isPreSelected',
        render: (text: boolean) => (text ? '✔' : '✘'),
    },
    { align: 'center', title: 'Display Order', dataIndex: 'displayOrder', key: 'displayOrder' },
    {
        align: 'center',
        title: 'Action',
        key: 'action',
        render: (record) => (
            <>
                <Button
                    className='default-btn-color m-1'
                    icon={<EditOutlined />}
                    onClick={() => handleEditValue(record)}
                >
                    Edit
                </Button>

                <Button
                    className='m-1 text-white bg-red-500'
                    icon={<DeleteOutlined />}
                    onClick={() => handleRemoveValue(record)}
                >
                    Remove
                </Button>
            </>
        ),
    },
]
