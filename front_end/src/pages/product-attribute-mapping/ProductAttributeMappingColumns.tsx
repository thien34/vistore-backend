import AppActions from '@/constants/AppActions'
import {
    ProductAttributeValueRequest,
    ProductProductAttributeMappingResponse,
} from '@/model/ProductProductAttributeMapping'
import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import { Link } from 'react-router-dom'
export const getProductAttributeValueColumns = (
    handleDeleteValue: (name: string) => void,
    handleOpenUpdateModal: (record: ProductAttributeValueRequest) => void,
): TableColumnsType<ProductAttributeValueRequest> => [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        render: (text) => text,
    },
    {
        width: '10%',
        title: 'Price adjustment',
        dataIndex: 'priceAdjustment',
        key: 'priceAdjustment',
        render: (text, record) => (record.priceAdjustmentPercentage ? `${text}%` : text),
    },
    {
        width: '10%',
        title: 'Weight adjustment',
        dataIndex: 'weightAdjustment',
        key: 'weightAdjustment',
    },
    {
        width: '10%',
        title: 'Is pre-selected',
        dataIndex: 'isPreSelected',
        key: 'isPreSelected',
        render: (text) => (text ? 'Yes' : 'No'),
        align: 'center',
    },
    {
        title: 'Picture',
        dataIndex: 'picture',
        align: 'center',
        key: 'display order',
    },
    {
        width: '10%',
        title: 'Display order',
        dataIndex: 'displayOrder',
        key: 'display order',
        align: 'center',
    },
    {
        width: '20%',
        title: 'Action',
        key: 'action',
        align: 'center',
        render: (_, record) => (
            <div className='flex flex-wrap justify-around'>
                <Button
                    className='default-btn-color m-1'
                    icon={<EditOutlined />}
                    onClick={() => handleOpenUpdateModal(record)}
                >
                    {AppActions.EDIT}
                </Button>
                <Button
                    className='default-btn-color m-1'
                    icon={<EditOutlined />}
                    onClick={() => handleDeleteValue(record.name)}
                >
                    {AppActions.DELETE}
                </Button>
            </div>
        ),
    },
]

export const getProductAttributeMappingColumns = (): TableColumnsType<ProductProductAttributeMappingResponse> => [
    {
        title: 'Attribute',
        dataIndex: 'nameProductAttribute',
        key: 'attribute',
        render: (text) => text,
    },
    {
        title: 'Text Prompt',
        dataIndex: 'textPrompt',
        key: 'text prompt',
    },
    {
        width: '10%',
        title: 'Is Required',
        dataIndex: 'isRequired',
        key: 'is required',
        align: 'center',
        render: (text) => (text ? 'Yes' : 'No'),
    },
    {
        title: 'Control Type',
        dataIndex: 'attributeControlTypeId',
        key: 'control type',
    },
    {
        width: '11%',
        align: 'center',
        title: 'Display Order',
        dataIndex: 'displayOrder',
        key: 'display order',
    },
    {
        width: '10%',
        title: 'Action',
        key: 'action',
        align: 'center',
        render: (_, record) => (
            <Link to={`/admin/products/product-attribute-mapping-update/${record.id}`}>
                <Button className='default-btn-color' icon={<EditOutlined />}>
                    {AppActions.EDIT}
                </Button>
            </Link>
        ),
    },
]
