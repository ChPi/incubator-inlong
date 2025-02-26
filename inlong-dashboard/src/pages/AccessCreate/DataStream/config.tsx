/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import React from 'react';
import { Divider } from 'antd';
import i18n from '@/i18n';
import { genBusinessFields, genDataFields } from '@/components/AccessHelper';
import { Storages } from '@/components/MetaData';

export const genFormContent = (currentValues, inlongGroupId, mqType) => {
  const extraParams = {
    inlongGroupId: inlongGroupId,
  };

  return [
    ...genDataFields(
      [
        {
          type: (
            <Divider orientation="left">
              {i18n.t('pages.AccessCreate.DataStream.config.Basic')}
            </Divider>
          ),
        },
        'inlongStreamId',
        'name',
        'description',
        // {
        //   type: (
        //     <Divider orientation="left">
        //       {i18n.t('pages.AccessCreate.DataStream.config.DataSources')}
        //     </Divider>
        //   ),
        // },
        // 'dataSourceType',
        // 'hasHigher',
        // 'isHybridSource',
        // 'isTableMapping',
        // 'dateOffset',
        // 'havePredefinedFields',
        // 'predefinedFields',
        'dataSourcesConfig',
        {
          type: (
            <Divider orientation="left">
              {i18n.t('pages.AccessCreate.DataStream.config.DataInfo')}
            </Divider>
          ),
        },
        'dataType',
        'dataEncoding',
        'dataSeparator',
        'rowTypeFields',
        {
          type: (
            <Divider orientation="left">
              {i18n.t('pages.AccessCreate.Business.config.AccessScale')}
            </Divider>
          ),
          visible: mqType === 'PULSAR',
        },
      ],
      currentValues,
      extraParams,
    ),
    ...genBusinessFields(['dailyRecords', 'dailyStorage', 'peakRecords', 'maxLength']).map(
      item => ({
        ...item,
        visible: mqType === 'PULSAR',
      }),
    ),
    ...genDataFields(
      [
        {
          type: (
            <Divider orientation="left">
              {i18n.t('pages.AccessCreate.DataStream.config.DataStorages')}
            </Divider>
          ),
        },
        'streamSink',
        ...Storages.map(item => `streamSink${item.value}`),
      ],
      currentValues,
      extraParams,
    ),
  ];
};
