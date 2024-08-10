import { Collapse, Tabs } from 'antd'
import ProductAttributeMappingConfigs from './ProductAttributeMappingConfigs'
import ProductAttributeMapping from './ProductAttributeMapping'

export default function ProductAttributeMappingManage() {
    return (
        <Collapse
            collapsible='header'
            defaultActiveKey={['1']}
            items={[
                {
                    key: '1',
                    label: ProductAttributeMappingConfigs.resourceKey,
                    children: (
                        <Tabs
                            defaultActiveKey='1'
                            type='card'
                            size='middle'
                            items={[
                                {
                                    key: '1',
                                    label: 'Attributes',
                                    children: <ProductAttributeMapping />,
                                },
                                {
                                    key: '2',
                                    label: 'Attribute combinations',
                                    children: 'Search',
                                },
                            ]}
                        />
                    ),
                },
            ]}
        />
    )
}
