import { PlusOutlined } from '@ant-design/icons'
import { Button, Radio, Select, Space, Splitter, Table, TableProps, Tag } from 'antd'

interface DataType {
    key: string
    name: string
    age: number
    address: string
    tags: string[]
}

const options = [
    { label: 'Tiền mặt', value: 'Apple' },
    { label: 'Chuyển khoản', value: 'Pear' },
]

const columns: TableProps<DataType>['columns'] = [
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
        render: (text) => <a>{text}</a>,
    },
    {
        title: 'Age',
        dataIndex: 'age',
        key: 'age',
    },
    {
        title: 'Address',
        dataIndex: 'address',
        key: 'address',
    },
    {
        title: 'Tags',
        key: 'tags',
        dataIndex: 'tags',
        render: (_, { tags }) => (
            <>
                {tags.map((tag) => {
                    let color = tag.length > 5 ? 'geekblue' : 'green'
                    if (tag === 'loser') {
                        color = 'volcano'
                    }
                    return (
                        <Tag color={color} key={tag}>
                            {tag.toUpperCase()}
                        </Tag>
                    )
                })}
            </>
        ),
    },
    {
        title: 'Action',
        key: 'action',
        render: (_, record) => (
            <Space size='middle'>
                <a>Invite {record.name}</a>
                <a>Delete</a>
            </Space>
        ),
    },
]

const data: DataType[] = [
    {
        key: '1',
        name: 'John Brown',
        age: 32,
        address: 'New York No. 1 Lake Park',
        tags: ['nice', 'developer'],
    },
    {
        key: '2',
        name: 'Jim Green',
        age: 42,
        address: 'London No. 1 Lake Park',
        tags: ['loser'],
    },
    {
        key: '3',
        name: 'Joe Black',
        age: 32,
        address: 'Sydney No. 1 Lake Park',
        tags: ['cool', 'teacher'],
    },
    {
        key: '3',
        name: 'Joe Black',
        age: 32,
        address: 'Sydney No. 1 Lake Park',
        tags: ['cool', 'teacher'],
    },
    {
        key: '3',
        name: 'Joe Black',
        age: 32,
        address: 'Sydney No. 1 Lake Park',
        tags: ['cool', 'teacher'],
    },
    {
        key: '3',
        name: 'Joe Black',
        age: 32,
        address: 'Sydney No. 1 Lake Park',
        tags: ['cool', 'teacher'],
    },
    {
        key: '3',
        name: 'Joe Black',
        age: 32,
        address: 'Sydney No. 1 Lake Park',
        tags: ['cool', 'teacher'],
    },
]

const onChange = (value: string) => {
    console.log(`selected ${value}`)
}

const onSearch = (value: string) => {
    console.log('search:', value)
}

export default function Bill() {
    return (
        <>
            <Splitter className='h-[48.6vh]'>
                <Splitter.Panel defaultSize='65%' min='55%' max='75%'>
                    <Table<DataType> columns={columns} dataSource={data} pagination={false} />
                </Splitter.Panel>
                <Splitter.Panel>
                    <div className='mx-4'>
                        <div className='flex justify-between'>
                            <div className=''>
                                Người bán: <span className='font-semibold'>Hoàng Duy Khánh</span>
                            </div>
                            <div className='flex gap-3'>
                                <div className=''>02/10/2024</div>
                                <div className=''>16:35</div>
                            </div>
                        </div>
                        <div className='flex gap-3 mt-4'>
                            <Select
                                className='w-full'
                                size='middle'
                                showSearch
                                placeholder='Select a customer'
                                optionFilterProp='label'
                                onChange={onChange}
                                onSearch={onSearch}
                                options={[
                                    {
                                        value: 'jack',
                                        label: 'Jack',
                                    },
                                    {
                                        value: 'lucy',
                                        label: 'Lucy',
                                    },
                                    {
                                        value: 'tom',
                                        label: 'Tom',
                                    },
                                ]}
                            />
                            <Button color='default' variant='filled'>
                                <PlusOutlined />
                            </Button>
                        </div>
                        <div className=''>
                            <div className='flex justify-between mt-2 text-base'>
                                <div className=''>Tổng tiền hàng</div>
                                <div className=''>63,000</div>
                            </div>
                            <div className='flex justify-between mt-2 text-base'>
                                <div className=''>Giảm giá</div>
                                <div className=''>12,000</div>
                            </div>
                            <div className='flex justify-between mt-2 text-base'>
                                <div className='font-semibold'>Khách cần trả</div>
                                <div className='font-semibold'>51,000</div>
                            </div>
                            <div className='flex justify-between mt-2 text-base'>
                                <div className='font-semibold'>Khách thanh toán</div>
                                <div className=''>44,000</div>
                            </div>
                        </div>
                        <div className='mt-2 flex gap-3'>
                            <Radio.Group block options={options} defaultValue='Apple' className='w-full items-center' />
                            <Button color='default' variant='filled'>
                                <PlusOutlined />
                            </Button>
                        </div>
                        <div className=''>
                            <div className='flex justify-between mt-2 text-base'>
                                <div className=''>Tiền thừa trả khách</div>
                                <div className=''>63,000</div>
                            </div>
                        </div>
                    </div>
                </Splitter.Panel>
            </Splitter>
        </>
    )
}
