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

import { pathToRegexp } from 'path-to-regexp';
import type { RouteProps as ReactRouteProps } from 'react-router-dom';

export interface RouteProps extends Omit<ReactRouteProps, 'component'> {
  component: () => Promise<{ default: any }>;
  childRoutes?: RouteProps[];
}

const routes: RouteProps[] = [
  {
    path: '/',
    component: () => import('@/pages/Login'),
    exact: true,
  },
  {
    path: '/login',
    component: () => import('@/pages/Login'),
    exact: true,
  },
  {
    path: '/access',
    component: () => import('@/pages/AccessDashboard'),
    exact: true,
    childRoutes: [
      {
        path: '/create/:id?',
        component: () => import('@/pages/AccessDetail'),
        exact: true,
      },
      {
        path: '/detail/:id',
        component: () => import('@/pages/AccessDetail'),
        exact: true,
      },
    ],
  },
  {
    path: '/consume',
    component: () => import('@/pages/ConsumeDashboard'),
    exact: true,
    childRoutes: [
      {
        path: '/create',
        component: () => import('@/pages/ConsumeCreate'),
        exact: true,
      },
      {
        path: '/detail/:id',
        component: () => import('@/pages/ConsumeDetail'),
        exact: true,
      },
    ],
  },
  {
    path: '/approvals',
    component: () => import('@/pages/Approvals'),
    exact: true,
    childRoutes: [
      {
        path: '/detail/:id',
        component: () => import('@/pages/ApprovalDetail'),
        exact: true,
      },
    ],
  },
  {
    path: '/dataSources',
    component: () => import('@/pages/DataSources'),
    exact: true,
  },
  {
    path: '/user',
    component: () => import('@/pages/UserManagement'),
    exact: true,
  },
  {
    component: () => import('@/pages/Error/404'),
  },
];

/**
 * In a route tree structure array, get all the routing path configuration simple array collection
 * @param {array} routes
 * @param {string} parentPath Root of current routes
 * @param {function} filterFunction Custom filter function, default none, that is, get all by default
 * @param {function} returnFunction Custom return function, default none, that is, return compiledPath by default
 * @return {array}
 */
function getRoutesPaths(
  routes: RouteProps[],
  parentPath = '',
  filterFunction?: (compiledPath: string, item: RouteProps) => boolean,
  returnFunction?: (compiledPath: string, item: RouteProps) => any,
): string[] {
  return routes.reduce((acc, item) => {
    const { path, childRoutes } = item;
    const compiledPath = `${parentPath}${path}`;
    const childPaths = childRoutes
      ? getRoutesPaths(childRoutes, compiledPath, filterFunction, returnFunction)
      : [];

    const currentReturn = returnFunction ? returnFunction(compiledPath, item) : compiledPath;
    return acc.concat(
      filterFunction && !filterFunction(compiledPath, item) ? [] : currentReturn,
      childPaths,
    );
  }, []);
}

// All routing path configuration of the project
const allRoutesPaths = getRoutesPaths(routes);

// The hash format of allRoutesPaths is purely for convenience and efficiency in use
const allRoutesPathsMap = allRoutesPaths.reduce(
  (acc, cur) => ({
    ...acc,
    [cur]: true,
  }),
  {},
);

// Determine whether the incoming pathname has a corresponding route
export function getPathnameExist(pathname: string): boolean {
  if (allRoutesPathsMap[pathname]) return true;
  return allRoutesPaths.some(route => pathToRegexp(route).test(pathname));
}

export default routes;
