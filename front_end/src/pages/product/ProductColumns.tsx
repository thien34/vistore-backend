import { ProductResponse } from '@/model/Product'
import { TableColumnsType } from 'antd'

export const getProductColumns = (): TableColumnsType<ProductResponse> => [
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
]
