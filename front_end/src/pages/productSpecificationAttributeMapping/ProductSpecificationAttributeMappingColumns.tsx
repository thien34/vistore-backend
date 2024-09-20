import { Button, TableColumnsType } from 'antd'
import { EditOutlined } from '@ant-design/icons'
import { ProductSpecificationAttributeMappingByProductResponse } from '@/model/ProductSpecificationAttributeMapping'
import AppActions from '@/constants/AppActions'

export const getProductSpecificationAttributeMappingColumns = (
    handleEdit: (record: ProductSpecificationAttributeMappingByProductResponse) => void,
    handleDelete: (id: number) => void,
): TableColumnsType<ProductSpecificationAttributeMappingByProductResponse> => [
    {
        title: 'Attribute Type',
        dataIndex: 'attributeType',
        key: 'attributeType',
        render: (text) => text ?? 'N/A',
    },
    {
        title: 'Attribute',
        dataIndex: 'specificationAttributeName',
        key: 'specificationAttributeName',
        render: (text) => text ?? 'N/A',
    },
    {
        title: 'Value',
        dataIndex: 'customValue',
        key: 'customValue',
        render: (text, record) =>
            record.attributeType === 'Custom Text'
                ? (text ?? 'N/A')
                : (record.specificationAttributeOptionName ?? 'N/A'),
    },
    {
        width: '10%',
        title: 'Show on Product Page',
        dataIndex: 'showOnProductPage',
        key: 'showOnProductPage',
        align: 'center',
        render: (text) => (text ? '✔' : '✘'),
    },
    {
        width: '10%',
        title: 'Display Order',
        dataIndex: 'displayOrder',
        key: 'displayOrder',
        align: 'center',
    },
    {
        width: '20%',
        title: 'Action',
        key: 'action',
        align: 'center',
        render: (_, record) => (
            <div className='flex flex-wrap justify-around'>
                <Button className='default-btn-color m-1' icon={<EditOutlined />} onClick={() => handleEdit(record)}>
                    {AppActions.EDIT}
                </Button>
                <Button
                    className='default-btn-color m-1'
                    icon={<EditOutlined />}
                    onClick={() => handleDelete(record.id)}
                >
                    {AppActions.DELETE}
                </Button>
            </div>
        ),
    },
]
