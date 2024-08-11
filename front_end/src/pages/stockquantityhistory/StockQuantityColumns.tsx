import { StockQuantityHistoryResponse } from '@/model/StockQuantityHistory'
import formatDate from '@/utils/DateConvertUtils'
import { TableColumnsType } from 'antd'

const getStockQuantityColumns = (): TableColumnsType<StockQuantityHistoryResponse> => [
    {
        width: '25%',
        title: 'Product ID',
        dataIndex: 'product',
        key: 'product',
        render: (_, record) => <span>{record.productId}</span>,
    },
    {
        width: '10%',
        title: 'QuantityAdjustment',
        dataIndex: 'quantityAdjustment',
        key: 'quantityAdjustment',
        render: (quantityAdjustment) => (quantityAdjustment > 0 ? `+${quantityAdjustment}` : `${quantityAdjustment}`),
    },
    {
        width: '10%',
        title: 'StockQuantity',
        dataIndex: 'stockQuantity',
        key: 'stockQuantity',
    },
    {
        width: '30%',
        title: 'Message',
        dataIndex: 'message',
        key: 'message',
    },
    {
        width: '25%',
        title: 'Created Date',
        dataIndex: 'createdDate',
        key: 'createdDate',
        render: (createdDate: Date) => formatDate(createdDate),
    },
]
export default getStockQuantityColumns
