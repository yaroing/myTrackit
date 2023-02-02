import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Monitoring from './monitoring';
import MonitoringDetail from './monitoring-detail';
import MonitoringUpdate from './monitoring-update';
import MonitoringDeleteDialog from './monitoring-delete-dialog';

const MonitoringRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Monitoring />} />
    <Route path="new" element={<MonitoringUpdate />} />
    <Route path=":id">
      <Route index element={<MonitoringDetail />} />
      <Route path="edit" element={<MonitoringUpdate />} />
      <Route path="delete" element={<MonitoringDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MonitoringRoutes;
