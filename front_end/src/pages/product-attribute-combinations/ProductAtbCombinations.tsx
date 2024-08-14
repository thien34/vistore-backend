import { Tabs, type TabsProps } from 'antd'
import ProductAtbCombinationsManage from './ProductAtbCombinationsManage'

const onChange = (key: string) => {
    console.log(key)
}

const items: TabsProps['items'] = [
    {
        key: '1',
        label: 'Tab 1',
        children: <ProductAtbCombinationsManage />,
    },
    {
        key: '2',
        label: 'Tab 2',
        children: 'Content of Tab Pane 2',
    },
    {
        key: '3',
        label: 'Tab 3',
        children: 'Content of Tab Pane 3',
    },
]

export default function ProductAtbCombinations() {
    return (
        <div>
            <Tabs defaultActiveKey='1' items={items} onChange={onChange} />
        </div>
    )
}
