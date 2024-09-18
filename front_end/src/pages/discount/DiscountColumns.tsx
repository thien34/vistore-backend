import ManagerPath from '@/constants/ManagerPath'
import { DiscountResponse } from '@/model/Discount'
import { EditOutlined } from '@ant-design/icons'
import { Button, TableColumnsType } from 'antd'
import dayjs from 'dayjs'
import { Link } from 'react-router-dom'
import DiscountConfigs from './DiscountConfigs'

const getDiscountColumns = (): TableColumnsType<DiscountResponse> => [
    {
        width: '25%',
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        align: 'center',
        render: (_, record) => <span>{record.name}</span>,
        sorter: (a, b) => a.name.localeCompare(b.name),
    },
    {
        width: '20%',
        title: 'Discount Type',
        dataIndex: 'discountType',
        key: 'discountType',
        align: 'center',
        render: (_, record) => <span>{record.discountTypeName}</span>,
        sorter: (a, b) => a.discountTypeName.localeCompare(b.discountTypeName),
    },
    {
        width: '10%',
        title: 'Discount',
        dataIndex: 'discount',
        key: 'discount',
        align: 'center',
        render: (_, record) => {
            const usePercentage = record.discountPercentage !== null
            return <span>{usePercentage ? `${record.discountPercentage}%` : `${record.discountAmount} USD`}</span>
        },
        sorter: (a, b) => {
            const aDiscount = a.discountPercentage !== null ? a.discountPercentage : a.discountAmount
            const bDiscount = b.discountPercentage !== null ? b.discountPercentage : b.discountAmount
            return (aDiscount ?? 0) - (bDiscount ?? 0)
        },
    },
    {
        width: '15%',
        title: 'Start Date',
        dataIndex: 'startDate',
        key: 'startDate',
        align: 'center',
        render: (_, record) => <span>{dayjs(record.startDateUtc).format(DiscountConfigs.dateTimeUTC)}</span>,
        sorter: (a, b) => dayjs(a.startDateUtc).unix() - dayjs(b.startDateUtc).unix(),
    },
    {
        width: '15%',
        title: 'End Date',
        dataIndex: 'endDate',
        key: 'endDate',
        align: 'center',
        render: (_, record) => <span>{dayjs(record.endDateUtc).format(DiscountConfigs.dateTimeUTC)}</span>,
        sorter: (a, b) => dayjs(a.endDateUtc).unix() - dayjs(b.endDateUtc).unix(),
    },
    {
        width: '10%',
        title: 'Times Used',
        dataIndex: 'timesUsed',
        key: 'timesUsed',
        align: 'center',
        render: (_, record) => <span>{record.limitationTimes}</span>,
        sorter: (a, b) => {
            const aTimes = a.limitationTimes ?? 0
            const bTimes = b.limitationTimes ?? 0
            return aTimes - bTimes
        },
    },
    {
        width: '5%',
        title: 'Is Active',
        dataIndex: 'isActive',
        key: 'isActive',
        align: 'center',
        render: (isActive) => (isActive ? '✔' : '✘'),
        sorter: (a, b) => Number(a.isActive) - Number(b.isActive),
    },
    {
        width: '10%',
        title: 'Action',
        key: 'action',
        align: 'center',
        render: (_, record) => (
            <Link to={`${ManagerPath.DISCOUNT_UPDATE.replace(':id', record.id.toString())}`}>
                <Button className='bg-[#374151] border-[#374151] text-white' icon={<EditOutlined />}>
                    Edit
                </Button>
            </Link>
        ),
    },
]

export default getDiscountColumns
